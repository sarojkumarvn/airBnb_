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
    private String[] photos ;
    private String[] amenities ;
    private HotelContactInfo  contactInfo ;
    private Boolean active ;

}
