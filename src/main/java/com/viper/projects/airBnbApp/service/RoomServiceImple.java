package com.viper.projects.airBnbApp.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viper.projects.airBnbApp.dto.RoomDto;
import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Room;
import com.viper.projects.airBnbApp.exception.ResourceNotFoundException;
import com.viper.projects.airBnbApp.repository.HotelRepository;
import com.viper.projects.airBnbApp.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RoomServiceImple implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(RoomDto roomDto , Long hotelId) {

        log.info("Creating new room");

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Hotel not found with id " + hotelId));

        Room room = modelMapper.map(roomDto , Room.class);
        room.setHotel(hotel);

        room = roomRepository.save(room);

        if (hotel.getActive()) {
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room , RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllTheRoomsInHotel(Long hotelId) {

        if (!hotelRepository.existsById(hotelId))
            throw new ResourceNotFoundException("Hotel not found " + hotelId);

        return roomRepository.findByHotelId(hotelId)
                .stream()
                .map(r -> modelMapper.map(r , RoomDto.class))
                .toList();
    }

    @Override
    public RoomDto getRoomById(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found " + roomId));

        return modelMapper.map(room , RoomDto.class);
    }

    @Override
    public void deleteRoomById(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found " + roomId));

        inventoryService.deleteFutureInventories(room);
        roomRepository.delete(room);
    }
}