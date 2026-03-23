package com.viper.projects.airBnbApp.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Pageable;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.dto.HotelDto;
import com.viper.projects.airBnbApp.dto.HotelSearchRequest;
import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Inventory;
import com.viper.projects.airBnbApp.entity.Room;
import com.viper.projects.airBnbApp.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class InventoryServiceImple implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper ;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for (; !today.isAfter(endDate);today = today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(BigDecimal.ZERO)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgefactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);

        }

    }



    @Override
    public void deleteAllInventories(Room room) {
        
        inventoryRepository.deleteByRoom(room);

        

    }

    @Override
    // we need to get the hotels which have at least one room who has the space for the user from the start date to the end date 
    public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage() , hotelSearchRequest.getSize());

        LocalDate startdate = hotelSearchRequest.getStartDate();
        LocalDate endDate = hotelSearchRequest.getEndDate();
        String city = hotelSearchRequest.getCity() ;
        Integer roomCount = hotelSearchRequest.getRoomsCount() ;


        

        Long dateCount = ChronoUnit.DAYS.between(startdate , endDate) + 1 ;

       Page<Hotel> hotelPage= inventoryRepository.findHotelsByAvailableInventory(city, startdate,endDate, roomCount,dateCount, pageable);

       return hotelPage.map((e) -> modelMapper.map(e , HotelDto.class));
        
       

        

    }





}
