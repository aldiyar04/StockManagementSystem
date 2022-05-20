package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.SaleCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.TxItemCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.SaleResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Customer;
import kz.iitu.itse1910.variant2issenbayev.entity.Product;
import kz.iitu.itse1910.variant2issenbayev.entity.SaleTransaction;
import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import kz.iitu.itse1910.variant2issenbayev.entity.TransactionItem;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.mapper.SaleMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.TransactionRepository;
import kz.iitu.itse1910.variant2issenbayev.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SaleService {
    private final TransactionRepository txRepo;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CustomerSettingsService customerSettingsService;

    public List<SaleResp> getAllSales() {
        return txRepo.findAllSales().stream()
                .map(SaleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<SaleResp> getSalesByCustomer(long customerId) {
        Customer customer = customerService.getByIdOrThrow(customerId);
        return txRepo.findAllSalesByCustomer(customer).stream()
                .map(SaleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public SaleResp getSaleById(long id) {
        SaleTransaction saleTx = getByIdOrThrow(id);
        return SaleMapper.INSTANCE.toDto(saleTx);
    }

    public SaleResp createSale(SaleCreationReq creationReq) {
        Customer customer = customerService.getByIdOrThrow(creationReq.getCustomerId());

        // init "saleTx"
        SaleTransaction saleTx = SaleTransaction.builder()
                .customer(customer)
                .status(Transaction.Status.COMPLETED)
                .createdBy(getPrincipalUser())
                .build();
        List<TransactionItem> items = mapToTransactionItems(creationReq.getItems(), saleTx);
        saleTx.setItems(items);

        BigDecimal netAmount = items.stream().map(TransactionItem::getNetAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        saleTx.setNetAmount(netAmount);


        // add bonuses for customer
        BigDecimal bonusPercentage = getBonusPercentage();
        BigDecimal bonusAmount = netAmount.multiply(bonusPercentage);
        customer.addBonuses(bonusAmount);

        BigDecimal profitFromSale = calcProfitFromSale(items);
        customer.addToNetProfit(profitFromSale);

        addSpecialBonusIfNecessaryConditionsMet(customer);

        return saveSaleAndMapToDto(saleTx);
    }

    private List<TransactionItem> mapToTransactionItems(List<TxItemCreationReq> itemCreationReqs,
                                                        SaleTransaction saleTx) {
        return itemCreationReqs.stream()
                .map(txItemReq -> {
                    Product product = productService.getByIdOrThrow(txItemReq.getProductId());
                    BigDecimal quantity = txItemReq.getQuantity();

                    // net amount and UOM are initialized in the builder from the supplied fields:
                    return TransactionItem.builder()
                            .transaction(saleTx)
                            .product(product)
                            .quantity(quantity)
                            .build();
                }).collect(Collectors.toList());
    }

    private BigDecimal calcProfitFromSale(List<TransactionItem> saleItems) {
        return saleItems.stream()
                .map(item -> {
                    Product product = item.getProduct();

                    BigDecimal wholesalePrice = product.getWholesalePrice();
                    BigDecimal retailPrice = product.getRetailPrice();
                    BigDecimal quantity = product.getQuantity();

                    BigDecimal profitFromOneProduct = retailPrice.subtract(wholesalePrice);
                    return profitFromOneProduct.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void addSpecialBonusIfNecessaryConditionsMet(Customer customer) {
        BigDecimal specBonusIntervalProfit =  customerSettingsService.getCustomerSpecialBonusSettings()
                .getIntervalProfitAmount();
        int timesGivenSpecBonus = customer.getTimesGivenSpecialBonus();
        timesGivenSpecBonus++;
        customer.setTimesGivenSpecialBonus(timesGivenSpecBonus);

        BigDecimal specBonusTargetMinProfit = specBonusIntervalProfit.multiply(
                new BigDecimal(timesGivenSpecBonus)
        );
        if (customer.getNetProfit().compareTo(specBonusTargetMinProfit) >= 0) {
            BigDecimal specBonusProfitPercents = getProfitPercentageForSpecialBonus();
            BigDecimal specBonus = customer.getNetProfit().multiply(specBonusProfitPercents);
            customer.addBonuses(specBonus);
        }
    }

    private BigDecimal getBonusPercentage() {
        int bonusPercentsOutOf100 = customerSettingsService.getCustomerBonusPercentage();
        return convertToBigDecimalPercentage(bonusPercentsOutOf100);
    }

    private BigDecimal getProfitPercentageForSpecialBonus() {
        int specBonusPercentsOutOf100 = customerSettingsService.getCustomerSpecialBonusSettings()
                .getPercentProfitFromCustomer();
        return convertToBigDecimalPercentage(specBonusPercentsOutOf100);
    }

    private BigDecimal convertToBigDecimalPercentage(int percentsOutOf100) {
        double percentage = percentsOutOf100 / 100.0;
        return new BigDecimal(percentage);
    }

    public SaleResp refundSale(long id) {
        SaleTransaction saleTx = getByIdOrThrow(id);

        BigDecimal refundPercentage = getCustomerRefundPercentage();
        BigDecimal refundAmount = saleTx.getNetAmount().multiply(refundPercentage);
        saleTx.setRefundAmount(refundAmount);

        saleTx.setStatus(Transaction.Status.REFUNDED);
        return saveSaleAndMapToDto(saleTx);
    }

    private BigDecimal getCustomerRefundPercentage() {
        return convertToBigDecimalPercentage(
                customerSettingsService.getCustomerRefundPercentage()
        );
    }

    public void deleteSale(long id) {
        SaleTransaction saleTx = getByIdOrThrow(id);
        txRepo.delete(saleTx);
    }

    private SaleTransaction getByIdOrThrow(long id) {
        Transaction tx = txRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Sale with id " + id + " does not exist"));
        if (!(tx instanceof SaleTransaction)) {
            throw new RecordNotFoundException("Sale with id " + id + " does not exist");
        }
        return (SaleTransaction) tx;
    }

    private SaleResp saveSaleAndMapToDto(SaleTransaction saleTx) {
        saleTx = (SaleTransaction) txRepo.save(saleTx);
        return SaleMapper.INSTANCE.toDto(saleTx);
    }

    private User getPrincipalUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }
}
