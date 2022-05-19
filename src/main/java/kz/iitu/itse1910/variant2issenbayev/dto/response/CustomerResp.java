package kz.iitu.itse1910.variant2issenbayev.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CustomerResp {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;
    private final String cardNumber;
    private final BigDecimal bonusBalance;
}
