package kz.iitu.itse1910.variant2issenbayev.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResp {
    private final Long id;
    private final String name;
    private final String description;
    private final Long categoryId;
    private final String categoryName;
    private final Long supplierId;
    private final String supplierName;
    private final BigDecimal wholesalePrice;
    private final BigDecimal retailPrice;
    private final Integer quantity;
    private final String purchaseUom;
    private final String saleUom;
    private final Integer uomConversionRate;
}
