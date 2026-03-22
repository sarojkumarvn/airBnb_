package com.viper.projects.airBnbApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viper.projects.airBnbApp.entity.Room;

public interface  RoomRepository extends JpaRepository<Room , Long > {
    
     List<Room> findByHotelId(Long hotelId);

}
