package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.UserDto;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.entity.Role;
import com.inthebytes.restaurantmanager.entity.User;
import com.inthebytes.restaurantmanager.mapper.RestaurantMapper;
import com.inthebytes.restaurantmanager.mapper.UserMapper;
import com.inthebytes.restaurantmanager.repository.RestaurantDao;
import com.inthebytes.restaurantmanager.repository.RoleDao;
import com.inthebytes.restaurantmanager.repository.UserDao;

@Service
public class RestaurantAccountService {
	
	@Autowired
	private RestaurantDao restaurantRepo;
	
	@Autowired
	private RoleDao roleRepo;
	
	@Autowired
	private UserDao userRepo;
	
	@Autowired
	private RestaurantMapper restaurantMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	private Role findManagerRole() {
		Role role = roleRepo.findByName("restaurant");
		if (role != null)
			return role;
		role = roleRepo.findByName("manager");
		return role;
	}
	
	public RestaurantDTO addManager(Long restaurantId, UserDto user) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		User userEntity = userRepo.findByUsername(user.getUsername());
		
		if (userEntity == null || restaurant == null)
			return null;
		userEntity.setRole(findManagerRole());
		userEntity.setActive(true);
		userEntity = userRepo.save(userEntity);
		
		restaurant.getManager().add(userEntity);
		return restaurantMapper.convert(restaurantRepo.save(restaurant));
		
	}
	
	public RestaurantDTO addManager(Long restaurantId, Long userId) {
		User userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			return null;
		return addManager(restaurantId, userMapper.convert(userEntity));
	}
	
	public RestaurantDTO removeManager(Long restaurantId, UserDto user) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		User userEntity = userRepo.findByUsername(user.getUsername());
		if (userEntity == null || restaurant == null)
			return null;
		
		userEntity.setActive(false);
		userEntity = userRepo.save(userEntity);
		
		if (restaurant.getManager().remove(userEntity)) {
			return restaurantMapper.convert(restaurantRepo.save(restaurant));
		}
		return null;
	}
	
	public RestaurantDTO removeManager(Long restaurantId, Long userId) {
		User userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			return null;
		return removeManager(restaurantId, userMapper.convert(userEntity));
	}

}
