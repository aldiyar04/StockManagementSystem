package kz.iitu.itse1910.variant2issenbayev.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierResp {
    private final Long id;
    private final String name;
    private final String phone;
    private final String email;
    private final String websiteUrl;
    private final String city;
    private final String street;
    private final String buildingNumber;
}
