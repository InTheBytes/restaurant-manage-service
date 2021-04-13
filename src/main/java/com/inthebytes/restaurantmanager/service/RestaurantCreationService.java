package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Restaurant;

@Service
public class RestaurantCreationService {
	
	@Autowired
	private RestaurantDTO restaurantRepo;

	public Restaurant startRestaurant(Restaurant restaurant) {
		// TODO Auto-generated method stub
		return null;
	}
}
