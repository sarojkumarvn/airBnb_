package com.viper.projects.airBnbApp.strategy;

import java.math.BigDecimal;



import com.viper.projects.airBnbApp.entity.Inventory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped ;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {

        BigDecimal price = wrapped.calculatePrice(inventory);
        double occupancyRate = inventory.getBookedCount() / inventory.getTotalCount() ;
        if(occupancyRate > 0.8) {
            price = price.multiply(BigDecimal.valueOf(1.2));

        }

        
        return price ;
    }


}
