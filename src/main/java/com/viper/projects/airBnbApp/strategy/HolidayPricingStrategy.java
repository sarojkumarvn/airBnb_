package com.viper.projects.airBnbApp.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.entity.Inventory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isHoliday = true; // add an api here

        if (isHoliday) {
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }

}
