package com.viper.projects.airBnbApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viper.projects.airBnbApp.entity.Room;

public interface  RoomRepository extends JpaRepository<Room , Long > {

}
