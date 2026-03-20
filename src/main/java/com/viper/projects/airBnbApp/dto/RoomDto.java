package com.viper.projects.airBnbApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.viper.projects.airBnbApp.entity.Hotel;

import lombok.Data;

@Data
public class RoomDto {
    private Long id ;
    private String type ;
    private BigDecimal basePrice ;
    private String[] photos ;
    private String[] amenities ;
    private Integer totalcount ;
    private Integer capacity ;


}
