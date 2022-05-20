package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.SaleCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.SaleResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Sale;
import kz.iitu.itse1910.variant2issenbayev.entity.SaleTransaction;
import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordUndeletableException;
import kz.iitu.itse1910.variant2issenbayev.mapper.SaleMapper;
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

    public List<SaleResp> getAllSales() {
        return txRepo.findAllSales().stream()
                .map(SaleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public SaleResp getSaleById(long id) {
        SaleTransaction saleTx = getByIdOrThrow(id);
        return SaleMapper.INSTANCE.toDto(saleTx);
    }

    public SaleResp createSale(SaleCreationReq creationReq) {
        SaleTransaction saleTx = SaleMapper.INSTANCE.toEntity(creationReq);
        return saveSaleAndMapToDto(saleTx);
    }

    public SaleResp refundSale(long id) {
        SaleTransaction sale = getByIdOrThrow(id);
        return saveSaleAndMapToDto(sale);
    }

    public void deleteSale(long id) {
        Sale sale = getByIdOrThrow(id);

        if (sale.hasAssociatedProducts()) {
            throw new RecordUndeletableException("Sale " + sale.getName() + " cannot be deleted " +
                    "since there are products associated with it");
        }

        txRepo.delete(sale);
    }

    private SaleTransaction getByIdOrThrow(long id) {
        Transaction tx = txRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Sale with id " + id + " does not exist"));
        if (!(tx instanceof SaleTransaction)) {
            throw new RecordNotFoundException("Sale with id " + id + " does not exist");
        }
        return (SaleTransaction) tx;
    }

    private SaleResp saveSaleAndMapToDto(Sale sale) {
        sale = txRepo.save(sale);
        return SaleMapper.INSTANCE.toDto(sale);
    }
}
