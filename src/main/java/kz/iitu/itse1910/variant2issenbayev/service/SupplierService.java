package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.SupplierDto;
import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordUndeletableException;
import kz.iitu.itse1910.variant2issenbayev.mapper.SupplierMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(SupplierMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public SupplierDto getSupplierById(long id) {
        Supplier supplier = getByIdOrThrow(id);
        return SupplierMapper.INSTANCE.toDto(supplier);
    }

    public SupplierDto createSupplier(SupplierDto creationReq) {
        throwIfAlreadyTaken(creationReq.getName());
        Supplier supplier = SupplierMapper.INSTANCE.toEntity(creationReq);
        return saveSupplierAndMapToDto(supplier);
    }

    public SupplierDto updateSupplier(long id, SupplierDto updateReq) {
        Supplier supplier = getByIdOrThrow(id);

        String newSupplierName = updateReq.getName();
        if (!supplier.getName().equals(newSupplierName)) {
            throwIfAlreadyTaken(newSupplierName);
        }

        SupplierMapper.INSTANCE.updateEntityFromDto(supplier, updateReq);
        return saveSupplierAndMapToDto(supplier);
    }

    public void deleteSupplier(long id) {
        Supplier supplier = getByIdOrThrow(id);

        if (supplier.hasAssociatedData()) {
            throw new RecordUndeletableException("Supplier " + supplier.getName() + " cannot be deleted " +
                    "since there is data associated with them");
        }

        supplierRepository.delete(supplier);
    }

    private Supplier getByIdOrThrow(long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Supplier with id " + id + " does not exist"));
    }

    private void throwIfAlreadyTaken(String supplierName) {
        if (supplierRepository.findByName(supplierName).isPresent()) {
            throw new RecordAlreadyExistsException("Supplier " + supplierName + " already exists");
        }
    }

    private SupplierDto saveSupplierAndMapToDto(Supplier supplier) {
        supplier = supplierRepository.save(supplier);
        return SupplierMapper.INSTANCE.toDto(supplier);
    }
}
