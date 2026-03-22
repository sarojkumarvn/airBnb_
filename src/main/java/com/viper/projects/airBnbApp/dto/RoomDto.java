package com.viper.projects.airBnbApp.dto;

import java.math.BigDecimal;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RoomDto {
    private Long id ;
    private String type ;
    private BigDecimal basePrice ;
    private String[] photos ;
    private String[] amenities ;
    private Integer totalCount ;
    private Integer capacity ;


}
