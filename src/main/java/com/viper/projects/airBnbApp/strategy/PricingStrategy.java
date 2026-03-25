package com.viper.projects.airBnbApp.strategy;

import java.math.BigDecimal;

import com.viper.projects.airBnbApp.entity.Inventory;





public interface PricingStrategy {
 

    BigDecimal calculatePrice(Inventory inventory);

}
