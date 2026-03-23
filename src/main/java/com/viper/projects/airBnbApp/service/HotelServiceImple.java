package com.viper.projects.airBnbApp.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.dto.HotelDto;
import com.viper.projects.airBnbApp.dto.HotelInfoDto;
import com.viper.projects.airBnbApp.dto.RoomDto;
import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Room;
import com.viper.projects.airBnbApp.exception.ResourceNotFoundException;
import com.viper.projects.airBnbApp.repository.HotelRepository;
import com.viper.projects.airBnbApp.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImple implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService ;
    private final RoomRepository  roomRepository ;

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

    @Override
    public HotelDto updateHotelById(Long id , HotelDto hotelDto ) {
        log.info("Getting the hotel with id : {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel not found with the id : " + id));
        modelMapper.map(hotelDto , hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel  , HotelDto.class);



    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                       .orElseThrow(()-> new ResourceNotFoundException("Hotel does not exist with the Id : {}" + id ));
      

                       hotelRepository.deleteById(id);
                       for(Room room : hotel.getRooms()) {
                           inventoryService.deleteAllInventories(room);            
                           roomRepository.deleteById(room.getId());
                        }
               
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {

        log.info("Activating the hotel with ID : {}" , hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
                       .orElseThrow(()-> new ResourceNotFoundException("Hotel does not exist with the Id : {}" + hotelId));
                       
        hotel.setActive(true);
       

        // Assuming doinig it once 
        for ( Room room : hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
            
        }


    }


    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId){
        Hotel hotel = hotelRepository.findById(hotelId)
                       .orElseThrow(()-> new ResourceNotFoundException("Hotel does not exist with the Id : {}" + hotelId));
        List<RoomDto> rooms = hotel.getRooms()
        .stream()
        .map((e) -> modelMapper.map(e , RoomDto.class)).toList(); 
        
        
        return new HotelInfoDto(modelMapper.map(hotel , HotelDto.class) , rooms);
        
    } 



}
