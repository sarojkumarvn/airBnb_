package com.viper.projects.airBnbApp.dto;

import com.viper.projects.airBnbApp.entity.HotelContactInfo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HotelDto {
    private Long id  ;
    private String name ;
    private String city ;
    private HotelContactInfo  contactInfo ;
    private String[] amenities ;
    private String[] photos ;
    private Boolean active ;

}
