package com.viper.projects.airBnbApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viper.projects.airBnbApp.entity.Hotel;

public interface HotelRepository extends JpaRepository <Hotel , Long > {

    
} 