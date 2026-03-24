package com.viper.projects.airBnbApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viper.projects.airBnbApp.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest , Long> {
    

}
