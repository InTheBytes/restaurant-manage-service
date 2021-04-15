package com.inthebytes.restaurantmanager.service;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.repository.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	public Restaurant createRestaurant(Restaurant restaurant) throws IllegalArgumentException, EntityExistsException {
		Restaurant stored;

		try {
			stored = restaurantRepo.findByName(restaurant.getName());
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}

		if (stored != null)
			throw new EntityExistsException();

		try {
			return restaurantRepo.save(restaurant);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException();
		}
	}

		public Boolean deleteRestaurant(Long restaurantId) {
			Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
			if (restaurant == null)
				return false;
			restaurantRepo.delete(restaurant);
			return true;
		}
	}
