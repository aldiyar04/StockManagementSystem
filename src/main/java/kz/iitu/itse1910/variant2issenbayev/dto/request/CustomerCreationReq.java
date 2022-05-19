package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CustomerCreationReq {
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;
    private final String cardNumber;
    private final BigDecimal bonusBalance;
}
