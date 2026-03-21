package com.viper.projects.airBnbApp.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.dto.RoomDto;
import com.viper.projects.airBnbApp.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor

public class RoomServiceImple implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;


    @Override
    public RoomDto createNewRoom(RoomDto roomDto) {
        
        return null;
    }

    @Override
    public List<RoomDto> getAllTheRoomsInHotel(Long hotelId) {
        return List.of();
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        return null;
    }

    @Override
    public void deleteRoomById(Long roomId) {

    }

}