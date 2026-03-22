package com.viper.projects.airBnbApp.service;

import java.util.List;

import com.viper.projects.airBnbApp.dto.RoomDto;
import com.viper.projects.airBnbApp.entity.Room;

public interface RoomService {
    RoomDto createNewRoom(RoomDto roomDto , Long hotelId);
    List<RoomDto> getAllTheRoomsInHotel(Long hotelid);
    RoomDto getRoomById(Long roomId); 
    void deleteRoomById(Long roomId);   
  
}