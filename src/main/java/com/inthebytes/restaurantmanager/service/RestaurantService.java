package com.inthebytes.restaurantmanager.service;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

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
		
		try {
			return restaurantRepo.save(restaurant);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException();
		}
	}
}
