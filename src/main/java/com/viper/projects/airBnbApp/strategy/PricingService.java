package com.viper.projects.airBnbApp.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.entity.Inventory;

@Service
public class PricingService {
    public BigDecimal calculateDynamicPricing(Inventory inventory) 
    {
        PricingStrategy pricingStrategy = new BasePricingStrategy();

        // Apply the additional strategies 
        // Decorated design pattern

        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy); // Got the final price 

        return pricingStrategy.calculatePrice(inventory) ;
    }

}
