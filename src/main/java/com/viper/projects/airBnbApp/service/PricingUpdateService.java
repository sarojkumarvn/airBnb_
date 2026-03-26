package com.viper.projects.airBnbApp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.HotelMinPrice;
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

    @Scheduled(cron = "*/5 * * * *")  // This run in every five minutes 
    public void updatePrices(){
        int page = 0  ;
        int batchSize = 100 ;

        

        while ( true ) 

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
        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel , startDate , endDate); // here we got the inventory list 

        updateInventoryPrices(inventoryList);
        updateHotelMinPrice(hotel , inventoryList , startDate , endDate);

    }

    // Find the min inventory for this hote in a perticular date and update the hotelMinPrice on that date 
private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList,
                                 LocalDate startDate, LocalDate endDate) {

    Map<LocalDate, BigDecimal> dailyMinPrices = inventoryList.stream()
            .collect(Collectors.groupingBy(
                    Inventory::getDate,
                    Collectors.mapping(
                            Inventory::getPrice,
                            Collectors.minBy(Comparator.naturalOrder())
                    )
            ))
            .entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().orElse(BigDecimal.ZERO)
            ));

 
    List<HotelMinPrice> hotelPrices = new ArrayList<>();

    dailyMinPrices.forEach((date, price) -> {
        HotelMinPrice hotelPrice = hotelMinPriceRepository
                .findByHotelAndDate(hotel, date)
                .orElse(new HotelMinPrice(hotel, date));

        hotelPrice.setPrice(price);
        hotelPrices.add(hotelPrice);
    });

   
    hotelMinPriceRepository.saveAll(hotelPrices);
}


    private void updateInventoryPrices(List<Inventory> inventoryList) {
        inventoryList.forEach(inventory-> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);

        });
        inventoryRepository.saveAll(inventoryList);


    }

}
