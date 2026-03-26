package com.viper.projects.airBnbApp.dto;


import java.time.LocalDate;



import lombok.Data;

@Data


// These data should be transfered during the booking time 
public class BookingRequest {
    private Long hotelId ;
    private Long roomId ;
    private LocalDate checkInDate ;
    private LocalDate checkOutDate ;
    private Integer roomsCount ;

}
