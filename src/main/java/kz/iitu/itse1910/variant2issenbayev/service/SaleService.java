package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.response.SaleResp;
import kz.iitu.itse1910.variant2issenbayev.dto.response.TxItemResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Customer;
import kz.iitu.itse1910.variant2issenbayev.entity.SaleTransaction;
import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.mapper.SaleMapper;
import kz.iitu.itse1910.variant2issenbayev.mapper.TxItemMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SaleService {
    private final TransactionRepository txRepo;
    private final CustomerService customerService;

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
        SaleResp saleResp = SaleMapper.INSTANCE.toDto(saleTx);

        List<TxItemResp> itemResps = saleTx.fetchItems().stream()
                .map(TxItemMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        saleResp.setItems(itemResps);

        return saleResp;
    }

//    public SaleResp createSale(SaleCreationReq creationReq) {
//        return saveSaleAndMapToDto(saleTx);
//    }
//
//    public SaleResp refundSale(long id) {
//        SaleTransaction saleTx = getByIdOrThrow(id);
//        return saveSaleAndMapToDto(saleTx);
//    }

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
}
