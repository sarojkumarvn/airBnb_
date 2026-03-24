package com.viper.projects.airBnbApp.dto;

import java.util.Set;

import com.viper.projects.airBnbApp.entity.Booking;
import com.viper.projects.airBnbApp.entity.User;
import com.viper.projects.airBnbApp.entity.enums.Gender;

import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
