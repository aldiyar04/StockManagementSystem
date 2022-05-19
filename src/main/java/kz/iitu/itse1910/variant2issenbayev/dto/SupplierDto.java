package kz.iitu.itse1910.variant2issenbayev.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
    private final String name;
    private final String phone;
    private final String email;
    private final String websiteUrl;
    private final String city;
    private final String street;
    private final String buildingNumber;
}
