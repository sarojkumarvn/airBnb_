package com.viper.projects.airBnbApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.cglib.core.Local;

import lombok.Data;

@Data


// These daat should be transfered during the booking time 
public class BookingRequest {
    private Long hotelId ;
    private Long roomId ;
    private LocalDate checkInDate ;
    private LocalDate checkOutDate ;
    private Integer roomsCount ;

}
