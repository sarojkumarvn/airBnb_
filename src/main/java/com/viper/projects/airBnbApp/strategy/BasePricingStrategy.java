package com.viper.projects.airBnbApp.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.entity.Inventory;


@Service
public class BasePricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        
        return inventory.getRoom().getBasePrice() ;
    }

}
