package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.FoodModel;

@Repository
public interface FoodDTO extends JpaRepository<FoodModel, Long>{
	FoodModel findByFoodId(Long id);
	FoodModel findByName(String name);
}
