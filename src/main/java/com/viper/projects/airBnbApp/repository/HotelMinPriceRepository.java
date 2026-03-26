package com.viper.projects.airBnbApp.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.viper.projects.airBnbApp.dto.HotelPriceDto;
import com.viper.projects.airBnbApp.entity.HotelMinPrice;

public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice , Long > {


    // Here we have hotelMinPrice table which have the 10000 hotels for next 1 year it will store teh min price for them 
    @Query("""
    SELECT  com.viper.projects.airBnbApp.dto.HotelPriceDto(i.hotel ,AVG(i.price))
    FROM HotelMinPrice i
    WHERE i..hotel.city = :city 
        AND i.date BETWEEN :startDate AND :endDate   
        AND i.hote.active = false
        GROUP BY i.hotel 
       

        
    """)

    Page<HotelPriceDto> findHotelsByAvailableInventory(
        @Param("city") String city ,
        @Param("startDate") LocalDate startDate ,
        @Param("endDate") LocalDate endDate ,
        @Param("roomsCount") Integer roomsCount ,
        @Param("dateCount") Long dateCount,
        Pageable pageable
        
    ) ;

}
