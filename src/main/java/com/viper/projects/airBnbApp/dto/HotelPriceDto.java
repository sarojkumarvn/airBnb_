package com.viper.projects.airBnbApp.dto;

import com.viper.projects.airBnbApp.entity.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelPriceDto {
    private Hotel hotel ;
    private Double price ;

}
