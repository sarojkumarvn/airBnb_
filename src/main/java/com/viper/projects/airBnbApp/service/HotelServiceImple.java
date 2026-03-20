package com.viper.projects.airBnbApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.dto.HotelDto;
import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.exception.ResourceNotFoundException;
import com.viper.projects.airBnbApp.repository.HotelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImple implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with id : {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID : {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {

        log.info("Getting the hotel with id : {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel not found with the id : " + id));

        return modelMapper.map(hotel , HotelDto.class);
    }

}
