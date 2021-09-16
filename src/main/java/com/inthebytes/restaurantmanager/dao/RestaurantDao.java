package com.inthebytes.restaurantmanager.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.Restaurant;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, String> {
	Restaurant findByRestaurantId(String id);
	Restaurant findByName(String name);
	Restaurant findByManagerUserId(String userId);
	Page<Restaurant> findAll(Pageable pageable);
}
