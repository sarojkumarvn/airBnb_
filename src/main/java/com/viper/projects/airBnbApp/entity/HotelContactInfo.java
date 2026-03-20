package com.viper.projects.airBnbApp.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable   //You want to store a group of fields inside another table without creating a separate table.
public class HotelContactInfo {
    private String address;
    private String phoneNumber ;
    private String email ;
    private String location;

}
