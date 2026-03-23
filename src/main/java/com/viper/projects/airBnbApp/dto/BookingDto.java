package com.viper.projects.airBnbApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Room;
import com.viper.projects.airBnbApp.entity.User;
import com.viper.projects.airBnbApp.entity.enums.BookingStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookingDto {

    private Long id ;
    private Hotel hotel ;
    private Room room ;
    private Integer roomCount ;
    private User user ;
    private Integer roomsCount ;
    private LocalDate checkInDate ;
    private LocalDate checkOutDate ;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;
    private BookingStatus bookingStatus ;
     private Set<GuestDto> guests ;


    



}
