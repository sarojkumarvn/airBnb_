package com.viper.projects.airBnbApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viper.projects.airBnbApp.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking , Long >{

}
