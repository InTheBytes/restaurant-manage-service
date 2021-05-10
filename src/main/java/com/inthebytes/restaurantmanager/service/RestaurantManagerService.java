package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.inthebytes.restaurantmanager.mapper.RestaurantMapper;
import com.inthebytes.restaurantmanager.repository.RestaurantDao;
import com.inthebytes.restaurantmanager.repository.RoleDao;
import com.inthebytes.restaurantmanager.repository.UserDao;

public class RestaurantManagerService {
	
	@Autowired
	private RestaurantDao restaurantRepo;
	
	@Autowired
	private RoleDao roleRepo;
	
	@Autowired
	private UserDao userRepo;
	
	@Autowired
	private RestaurantMapper restaurantMapper;

}
