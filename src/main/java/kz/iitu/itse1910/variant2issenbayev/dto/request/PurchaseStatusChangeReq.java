package kz.iitu.itse1910.variant2issenbayev.dto.request;

import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import lombok.Data;

@Data
public class PurchaseStatusChangeReq {
    private final Transaction.Status status;
}
