package com.viper.projects.airBnbApp.service;

import com.viper.projects.airBnbApp.dto.HotelDto;
import com.viper.projects.airBnbApp.dto.HotelInfoDto;



// This is the layer from where the functions / services were called 
public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto) ;


    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id , HotelDto hotelDto);

    void deleteHotelById(Long id );

    void activateHotel(Long id ) ;

    HotelInfoDto getHotelInfoById( Long hotelId);
    


}
