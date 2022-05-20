package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SaleCreationReq {
    private final Long customerId;
    private final List<TxItemCreationReq> items;
}
