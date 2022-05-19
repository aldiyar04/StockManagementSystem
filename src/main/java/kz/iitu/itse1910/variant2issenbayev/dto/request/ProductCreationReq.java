package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductCreationReq {
    private final String name;
    private final String description;
    private final Long categoryId;
    private final Long supplierId;
    private final BigDecimal wholesalePrice;
    private final BigDecimal retailPrice;
    private final BigDecimal quantity;
    private final String uomName;
    private final String purchaseUom;
    private final String saleUom;
    private final Integer uomConversionRate;
}
