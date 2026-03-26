package com.viper.projects.airBnbApp.service;


import org.springframework.data.domain.Page;


import com.viper.projects.airBnbApp.dto.HotelPriceDto;
import com.viper.projects.airBnbApp.dto.HotelSearchRequest;
import com.viper.projects.airBnbApp.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);


}
