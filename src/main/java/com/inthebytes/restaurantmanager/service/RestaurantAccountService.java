package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.restaurantmanager.dao.RoleDao;
import com.inthebytes.restaurantmanager.dao.UserDao;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;

@Service
public class RestaurantAccountService {
	
	@Autowired
	private RestaurantDao restaurantRepo;
	
	@Autowired
	private RoleDao roleRepo;
	
	@Autowired
	private UserDao userRepo;
	
	
	private Role findManagerRole() {
		Role role = roleRepo.findByName("restaurant");
		if (role != null)
			return role;
		role = roleRepo.findByName("manager");
		return role;
	}
	
	public RestaurantDto addManager(String restaurantId, UserDto user) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		User userEntity = userRepo.findByUsername(user.getUsername());
		
		if (userEntity == null || restaurant == null)
			return null;
		userEntity.setRole(findManagerRole());
		userEntity.setActive(true);
		userEntity = userRepo.save(userEntity);
		
		restaurant.getManager().add(userEntity);
		return RestaurantDto.convert(restaurantRepo.save(restaurant));
		
	}
	
	public RestaurantDto addManager(String restaurantId, String userId) {
		User userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			return null;
		return addManager(restaurantId, UserDto.convert(userEntity));
	}
	
	public RestaurantDto removeManager(String restaurantId, UserDto user) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		User userEntity = userRepo.findByUsername(user.getUsername());
		if (userEntity == null || restaurant == null)
			return null;
		
		userEntity.setActive(false);
		userEntity = userRepo.save(userEntity);
		
		if (restaurant.getManager().remove(userEntity)) {
			return RestaurantDto.convert(restaurantRepo.save(restaurant));
		}
		return null;
	}
	
	public RestaurantDto removeManager(String restaurantId, String userId) {
		User userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			return null;
		return removeManager(restaurantId, UserDto.convert(userEntity));
	}

}
