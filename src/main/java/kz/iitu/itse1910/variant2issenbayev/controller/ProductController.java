package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.request.ProductCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.ProductUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.ProductResp;
import kz.iitu.itse1910.variant2issenbayev.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductResp> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/categories/{categoryId}/products")
    public List<ProductResp> getAllProductsOfCategory(@PathVariable("categoryId") long categoryId) {
        return productService.getAllProductsOfCategory(categoryId);
    }

    @GetMapping("/suppliers/{supplierId}/products")
    public List<ProductResp> getAllProductsOfSupplier(@PathVariable("supplierId") long supplierId) {
        return productService.getAllProductsOfSupplier(supplierId);
    }

    @GetMapping("/products/{id}")
    public ProductResp getProductById(@PathVariable("id") long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/products")
    public ProductResp createProduct(@Valid @RequestBody ProductCreationReq creationReq) {
        return productService.createProduct(creationReq);
    }

    @PutMapping("/products/{id}")
    public ProductResp updateProduct(@PathVariable("id") long id, @Valid @RequestBody ProductUpdateReq updateReq) {
        return productService.updateProduct(id, updateReq);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
