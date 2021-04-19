package com.inthebytes.restaurantmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	Restaurant findByRestaurantId(Long id);
	Restaurant findByName(String name);
	List<Restaurant> findAllByName(String name);
}
