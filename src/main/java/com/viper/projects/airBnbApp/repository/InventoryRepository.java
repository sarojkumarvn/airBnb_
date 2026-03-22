package com.viper.projects.airBnbApp.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.viper.projects.airBnbApp.entity.Inventory;
import com.viper.projects.airBnbApp.entity.Room;

public interface InventoryRepository extends JpaRepository<Inventory , Long > {
    
    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.room = :room")
    void deleteByDateAfterAndRoom(LocalDate date  , Room room);
    

}
