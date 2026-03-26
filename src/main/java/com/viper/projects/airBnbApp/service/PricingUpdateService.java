package com.viper.projects.airBnbApp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Inventory;
import com.viper.projects.airBnbApp.repository.HotelMinPriceRepository;
import com.viper.projects.airBnbApp.repository.HotelRepository;
import com.viper.projects.airBnbApp.repository.InventoryRepository;
import com.viper.projects.airBnbApp.strategy.PricingService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor

@Slf4j
public class PricingUpdateService {

    //scheduler to update the inventory and the hotel min price tables every hour 
        private final HotelRepository hotelRepository ;
        private final InventoryRepository inventoryRepository ;
        private final HotelMinPriceRepository hotelMinPriceRepository ;
        private final PricingService pricingService ;


    public void updatePrices(){
        int page = 0  ;
        int batchSize = 100 ;

        

        while ( true) 

            {
                Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page, batchSize));
                if(hotelPage.isEmpty()){
                    break;
                }
                hotelPage.getContent().forEach(this::updateHotelPrices);
                page++;

            }

    }


    private void updateHotelPrices(Hotel hotel) {

        // From here we can get the inventory list of 1 year of each of the hotel 
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);
        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel , startDate , endDate); 
        updateInventoryPrices(inventoryList);
        updateHotelMinPrices(hotel , inventoryList , startDate , endDate);

    }

    private void updateHotelMinPrices(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate,
            LocalDate endDate) {
       
    }


    private void updateInventoryPrices(List<Inventory> inventoryList) {
        inventoryList.forEach(inventory-> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);

        });


    }

}
