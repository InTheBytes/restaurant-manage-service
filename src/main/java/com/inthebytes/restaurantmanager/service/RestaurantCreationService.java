package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.CustomizationDTO;
import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.GenreDTO;
import com.inthebytes.restaurantmanager.dto.HoursDTO;
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.ManagerDTO;
import com.inthebytes.restaurantmanager.dto.MenuDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;

@Service
public class RestaurantCreationService {
	
	@Autowired
	private CustomizationDTO customizationRepo;
	@Autowired
	private FoodDTO foodRepo;
	@Autowired
	private GenreDTO genreRepo;
	@Autowired
	private HoursDTO hoursRepo;
	@Autowired
	private LocationDTO locationRepo;
	@Autowired
	private ManagerDTO managerRepo;
	@Autowired
	private MenuDTO menuRepo;
	@Autowired
	private RestaurantDTO restaurantRepo;
	
	
}
