package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.CustomerSpecialBonusSettingsDto;
import kz.iitu.itse1910.variant2issenbayev.service.CustomerSettingsService;
import kz.iitu.itse1910.variant2issenbayev.validation.CheckPercentage;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/settings")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class SettingsController {
    private final CustomerSettingsService customerSettingsService;

    @GetMapping("/customerRefund")
    public int getCustomerRefundPercentage() {
        return customerSettingsService.getCustomerRefundPercentage();
    }

    @PutMapping("/customerRefund")
    public void setCustomerRefundPercentage(@RequestParam("percentage") @CheckPercentage int percentage) {
        customerSettingsService.setCustomerRefundPercentage(percentage);
    }

    @GetMapping("/customerBonus")
    public int getCustomerBonusPercentage() {
        return customerSettingsService.getCustomerBonusPercentage();
    }

    @PutMapping("/customerBonus")
    public void setCustomerBonusPercentage(@RequestParam("percentage") @CheckPercentage int percentage) {
        customerSettingsService.setCustomerBonusPercentage(percentage);
    }

    @GetMapping("/customerSpecialBonus")
    public CustomerSpecialBonusSettingsDto getCustomerSpecialBonusSettings() {
        return customerSettingsService.getCustomerSpecialBonusSettings();
    }

    @PutMapping("/customerSpecialBonus")
    public void setCustomerSpecialBonusSettings(@Valid @RequestBody CustomerSpecialBonusSettingsDto specBonusSettings) {
        customerSettingsService.setCustomerSpecialBonusSettings(specBonusSettings);
    }
}
