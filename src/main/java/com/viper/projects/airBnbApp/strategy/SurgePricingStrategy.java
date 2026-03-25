package com.viper.projects.airBnbApp.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.entity.Inventory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class SurgePricingStrategy implements PricingStrategy {
       private final PricingStrategy wrapped ;

       @Override
       public BigDecimal calculatePrice(Inventory inventory) {

        BigDecimal price = wrapped.calculatePrice(inventory); // this returns the basePrice from the basePricingStrategy
        
        return price.multiply(inventory.getSurgefactor());
        
       }

}
