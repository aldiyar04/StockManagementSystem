package kz.iitu.itse1910.variant2issenbayev.dto.response;

import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SaleResp {
    private final Long id;
    private final Long customerId;
    private final String customerName;
    private final BigDecimal netAmount;
    private final Transaction.Status status;
    private final BigDecimal refundAmount;
    private final LocalDateTime createdAt;
    private final Long createdById;
    private final String createdByUsername;
    private List<TxItemResp> items;
}
