package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TxItemCreationReq {
    private final Long productId;
    private final BigDecimal quantity;
}
