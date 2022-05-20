package kz.iitu.itse1910.variant2issenbayev.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TxItemResp {
    private final Long id;
    private final Long transactionId;
    private final Long productId;
    private final String productName;
    private final BigDecimal quantity;
    private final String purchaseUom;
    private final BigDecimal netAmount;
}
