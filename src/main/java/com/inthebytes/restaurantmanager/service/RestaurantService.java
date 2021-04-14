package com.inthebytes.restaurantmanager.service;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Restaurant;

@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantDTO restaurantRepo;

	public Restaurant createRestaurant(Restaurant restaurant) throws IllegalArgumentException, EntityExistsException {
		Restaurant stored;
		
		try {
			stored = restaurantRepo.findByName(restaurant.getName());
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}
		
		if (stored != null)
			throw new EntityExistsException();
			
		return restaurantRepo.save(restaurant);
	}
}
