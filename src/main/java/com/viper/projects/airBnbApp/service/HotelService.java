package com.viper.projects.airBnbApp.service;

import com.viper.projects.airBnbApp.dto.HotelDto;



// This is the layer from where the functions / services were called 
public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto) ;


    HotelDto getHotelById(Long id);
    


}
