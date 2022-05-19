package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.request.SupplierCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.SupplierUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.SupplierResp;
import kz.iitu.itse1910.variant2issenbayev.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public List<SupplierResp> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierResp getSupplierById(@PathVariable("id") long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    public SupplierResp createSupplier(@Valid @RequestBody SupplierCreationReq creationReq) {
        return supplierService.createSupplier(creationReq);
    }

    @PutMapping("/{id}")
    public SupplierResp updateSupplier(@PathVariable("id") long id, @Valid @RequestBody SupplierUpdateReq updateReq) {
        return supplierService.updateSupplier(id, updateReq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable("id") long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
