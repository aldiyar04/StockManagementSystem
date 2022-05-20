package kz.iitu.itse1910.variant2issenbayev.dto;

import kz.iitu.itse1910.variant2issenbayev.validation.CheckAmountPositive;
import kz.iitu.itse1910.variant2issenbayev.validation.CheckPercentage;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerSpecialBonusSettingsDto {
    @CheckPercentage
    private final int percentProfitFromCustomer;

    @CheckAmountPositive
    private final BigDecimal intervalProfitAmount;
}
