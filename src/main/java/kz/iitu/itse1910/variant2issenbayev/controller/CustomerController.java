package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.request.CustomerCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.CustomerUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.CustomerResp;
import kz.iitu.itse1910.variant2issenbayev.service.CustomerService;
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
@RequestMapping("/customers")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResp> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerResp getCustomerById(@PathVariable("id") long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerResp createCustomer(@Valid @RequestBody CustomerCreationReq creationReq) {
        return customerService.createCustomer(creationReq);
    }

    @PutMapping("/{id}")
    public CustomerResp updateCustomer(@PathVariable("id") long id, @Valid @RequestBody CustomerUpdateReq updateReq) {
        return customerService.updateCustomer(id, updateReq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
