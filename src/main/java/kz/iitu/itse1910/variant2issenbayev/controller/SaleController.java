package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.response.SaleResp;
import kz.iitu.itse1910.variant2issenbayev.service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @GetMapping("/sales")
    public List<SaleResp> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/customers/{customerId}/sales")
    public List<SaleResp> getSalesByCustomer(@PathVariable("customerId") long customerId) {
        return saleService.getSalesByCustomer(customerId);
    }

    @GetMapping("/sales/{id}")
    public SaleResp getSaleById(@PathVariable("id") long id) {
        return saleService.getSaleById(id);
    }

//    @PostMapping("/sales")
//    public SaleResp createSale(@Valid @RequestBody SaleCreationReq creationReq) {
//        return saleService.createSale(creationReq);
//    }
//
//    @PatchMapping("/sales/{id}")
//    public SaleResp refundSale(@PathVariable("id") long id) {
//        return saleService.refundSale(id);
//    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable("id") long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
