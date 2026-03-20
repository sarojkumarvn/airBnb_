package com.viper.projects.airBnbApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viper.projects.airBnbApp.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory , Long > {

}
