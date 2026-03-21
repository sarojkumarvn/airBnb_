package com.viper.projects.airBnbApp.service;

import java.util.List;

import com.viper.projects.airBnbApp.dto.RoomDto;

public interface RoomService {
    RoomDto createNewRoom(RoomDto roomDto);
    List<RoomDto> getAllTheRoomsInHotel(Long hotelid);
    RoomDto getRoomById(Long roomId); 
    void deleteRoomById(Long roomId);   
}