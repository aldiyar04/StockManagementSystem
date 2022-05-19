package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;


@Data
@Builder
public class ProductUpdateReq {
    private final String name;
    private final String description;
    private final Long categoryId;
    private final Long supplierId;
    private final BigDecimal wholesalePrice;
    private final BigDecimal retailPrice;
    private final BigDecimal quantity;
    private final String purchaseUom;
    private final String saleUom;
    private final Integer uomConversionRate;

    @AssertTrue(message = "If you want to update UOM, all the related fields must be set. The UOM fields are " +
    "purchaseUom, saleUom, uomConversionRate.")
    boolean isUomFieldsAllSetOrAllNotSet() {
        boolean areAllSet = isAllUomFieldsSet();
        boolean areAllNotSet = StringUtils.isNoneBlank(purchaseUom) && StringUtils.isNoneBlank(saleUom)
                && uomConversionRate != null;
        return areAllSet || areAllNotSet;
    }

    public boolean isAllUomFieldsSet() {
        return StringUtils.isBlank(purchaseUom) && StringUtils.isBlank(saleUom)
                && uomConversionRate == null;
    }
}
