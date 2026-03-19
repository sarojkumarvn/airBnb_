package com.viper.projects.airBnbApp.entity;

import java.util.Set;

import javax.management.relation.Role;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "app_user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @Column(unique = true ,nullable = false)
    private String email ;


    @Column(nullable = false)
    private String password ;



    private String name ;

    @ElementCollection(fetch = FetchType.LAZY) // it will make another table for us names app_user.roles
    @Enumerated(EnumType.STRING)
    private Set<Role> roles ;

    ;


}
