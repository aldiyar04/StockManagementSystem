package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.ProductCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.ProductUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.ProductResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Category;
import kz.iitu.itse1910.variant2issenbayev.entity.Product;
import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
import kz.iitu.itse1910.variant2issenbayev.entity.Uom;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordUndeletableException;
import kz.iitu.itse1910.variant2issenbayev.mapper.ProductMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    public List<ProductResp> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductResp> getAllProductsOfCategory(long categoryId) {
        Category category = categoryService.getByIdOrThrow(categoryId);
        return category.fetchProducts().stream()
                .map(ProductMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductResp> getAllProductsOfSupplier(long supplierId) {
        Supplier supplier = supplierService.getByIdOrThrow(supplierId);
        return supplier.fetchProducts().stream()
                .map(ProductMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public ProductResp getProductById(long id) {
        Product product = getByIdOrThrow(id);
        return ProductMapper.INSTANCE.toDto(product);
    }

    public ProductResp createProduct(ProductCreationReq creationReq) {
        throwIfAlreadyTaken(creationReq.getName());

        Product product = ProductMapper.INSTANCE.toEntity(creationReq);

        Category productCategory = categoryService.getByIdOrThrow(
                creationReq.getCategoryId()
        );
        Supplier productSupplier = supplierService.getByIdOrThrow(
                creationReq.getSupplierId()
        );
        Uom uom = ProductMapper.INSTANCE.toUomEntity(creationReq);

        product.setCategory(productCategory);
        product.setSupplier(productSupplier);
        product.setUom(uom);

        return saveProductAndMapToDto(product);
    }

    public ProductResp updateProduct(long id, ProductUpdateReq updateReq) {
        Product product = getByIdOrThrow(id);

        String newProductName = updateReq.getName();
        if (!product.getName().equals(newProductName)) {
            throwIfAlreadyTaken(newProductName);
        }

        ProductMapper.INSTANCE.updateEntityFromDto(product, updateReq);

        Long newCategoryId = updateReq.getCategoryId();
        Long newSupplierId = updateReq.getSupplierId();
        if (newCategoryId != null) {
            Category newCategory = categoryService.getByIdOrThrow(newCategoryId);
            product.setCategory(newCategory);
        }
        if (newSupplierId != null) {
            Supplier newSupplier = supplierService.getByIdOrThrow(newSupplierId);
            product.setSupplier(newSupplier);
        }
        if (updateReq.isAllUomFieldsSet()) {
            Uom uom = ProductMapper.INSTANCE.toUomEntity(updateReq);
            product.setUom(uom);
        }

        return saveProductAndMapToDto(product);
    }

    public void deleteProduct(long id) {
        Product product = getByIdOrThrow(id);

        if (product.hasAssociatedTransactions()) {
            throw new RecordUndeletableException("Product " + product.getName() + " cannot be deleted " +
                    "since there are transactions associated with it");
        }

        productRepository.delete(product);
    }

    private Product getByIdOrThrow(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product with id " + id + " does not exist"));
    }

    private void throwIfAlreadyTaken(String productName) {
        if (productRepository.findByName(productName).isPresent()) {
            throw new RecordAlreadyExistsException("Product " + productName + " already exists");
        }
    }

    private ProductResp saveProductAndMapToDto(Product product) {
        product = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(product);
    }
}
