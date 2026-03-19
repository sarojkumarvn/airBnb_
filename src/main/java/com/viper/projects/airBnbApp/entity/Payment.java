package com.viper.projects.airBnbApp.entity;

import java.math.BigDecimal;

import com.viper.projects.airBnbApp.entity.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @Column(unique = true , nullable = false)
    private String transactionId ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus ;

    @Column(nullable = false , precision = 10 , scale = 2 )
    private BigDecimal amount ;

 
    


}
