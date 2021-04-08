package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.RestaurantModel;

@Repository
public interface RestaurantDTO extends JpaRepository<RestaurantModel, Long> {
	RestaurantModel findByRestaurantId(Long id);
	RestaurantModel findByName(String name);
}
