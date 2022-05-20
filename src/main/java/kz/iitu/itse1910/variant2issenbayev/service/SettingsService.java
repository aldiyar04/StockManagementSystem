package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.controller.SettingsController;
import kz.iitu.itse1910.variant2issenbayev.dto.CustomerSpecialBonusSettingsDto;
import kz.iitu.itse1910.variant2issenbayev.validation.CheckPercentage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.prefs.Preferences;

@Service
public class SettingsService {
    private final Preferences prefs = Preferences.userNodeForPackage(SettingsController.class);

    public int getCustomerRefundPercentage() {
        return prefs.getInt("CustomerRefund_Percentage", 100);
    }

    public void setCustomerRefundPercentage(@RequestParam("percentage") @CheckPercentage int percentage) {
        prefs.putInt("CustomerRefund_Percentage", percentage);
    }

    public int getCustomerBonusPercentage() {
        return prefs.getInt("CustomerBonus_Percentage", 2);
    }

    public void setCustomerBonusPercentage(@RequestParam("percentage") @CheckPercentage int percentage) {
        prefs.putInt("CustomerBonus_Percentage", percentage);
    }

    public CustomerSpecialBonusSettingsDto getCustomerSpecialBonusSettings() {
        int percentProfitFromCustomer = prefs.getInt("CustomerSpecialBonus_PercentProfitFromCustomer", 20);
        BigDecimal intervalProfitAmount = new BigDecimal(
                prefs.get("CustomerSpecialBonus_IntervalProfitAmount", "500")
        );
        return new CustomerSpecialBonusSettingsDto(percentProfitFromCustomer, intervalProfitAmount);
    }

    public void setCustomerSpecialBonusSettings(@Valid @RequestBody CustomerSpecialBonusSettingsDto specBonusSettings) {
        prefs.putInt("CustomerSpecialBonus_PercentProfitFromCustomer", specBonusSettings.getPercentProfitFromCustomer());
        prefs.put("CustomerSpecialBonus_IntervalProfitAmount", specBonusSettings.getIntervalProfitAmount().toString());
    }
}
