package com.viper.projects.airBnbApp.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Inventory;
import com.viper.projects.airBnbApp.entity.Room;

import jakarta.persistence.LockModeType;

public interface InventoryRepository extends JpaRepository<Inventory , Long > {
    
  
    void deleteByRoom(Room room);


@Query("""
    SELECT DISTINCT i.hotel
    FROM Inventory i
    WHERE i.city = :city 
        AND i.date BETWEEN :startDate AND :endDate
        AND i.closed = false
        AND (i.totalCount - i.bookedCount) >= :roomsCount

    GROUP BY i.hotel , i.room
    HAVING COUNT(i.date) = :dateCount
        
    """)

    Page<Hotel> findHotelsByAvailableInventory(
        @Param("city") String city ,
        @Param("startDate") LocalDate startDate ,
        @Param("endDate") LocalDate endDate ,
        @Param("roomsCount") Integer roomsCount ,
        @Param("dateCount") Long dateCount,
        Pageable pageable
        
    ) ;
    @Query(
        """
        SELECT i 
        FROM Inventory i 
        WHERE i.room.id = :roomId 
        AND i.date BETWEEN :startDate AND :endDate
        AND i.closed = false
        AND (i.totalCount - i.bookedCount) >= :roomsCount

                
                """
    )
    @Lock(LockModeType.PESSIMISTIC_WRITE)  // By this we can put lock on a particular row 
    // This lock will be released when the transactional method is over 

    List<Inventory> findAndlockAvailableInventory(
        @Param("roomId") Long roomId ,
        @Param("startDate") LocalDate startDate ,
        @Param("endDate") LocalDate endDate ,
        @Param("roomsCount") Integer roomsCount 
    
    );
    

}
