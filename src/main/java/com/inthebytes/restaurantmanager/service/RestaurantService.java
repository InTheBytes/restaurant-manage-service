package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantDTO restaurantRepo;

	public Boolean delete(Long restaurantId) {
		// TODO Auto-generated method stub
		return null;
	}
}
