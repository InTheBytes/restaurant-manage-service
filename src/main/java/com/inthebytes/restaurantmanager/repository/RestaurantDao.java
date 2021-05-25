package com.inthebytes.restaurantmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.Restaurant;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {
	Restaurant findByRestaurantId(Long id);
	Restaurant findByName(String name);
}
