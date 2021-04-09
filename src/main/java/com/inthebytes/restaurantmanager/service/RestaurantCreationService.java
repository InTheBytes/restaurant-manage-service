package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.ManagerDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDTO;
import com.inthebytes.restaurantmanager.entity.Manager;
import com.inthebytes.restaurantmanager.entity.Restaurant;

@Service
public class RestaurantCreationService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private RestaurantVerificationService verification;

	@Autowired
	private ManagerDTO managerRepo;
	@Autowired
	private RoleDTO roleRepo;
	@Autowired
	private RestaurantDTO restaurantRepo;
	
	public Restaurant getRestaurantInProgress(Long id) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(id);
		if (restaurant == null)
			return null;
		if (restaurant.getManager().getIsActive())
			return null;
		return restaurant;
	}
	
	public Boolean isSaved(Long RestaurantId) {
		if (restaurantRepo.findByRestaurantId(RestaurantId) == null)
			return false;
		return true;
	}
	
	public Boolean trashRestaurantProgress(Long restaurantId) {
		Restaurant restaurant = getRestaurantInProgress(restaurantId);
		if (restaurant == null)
			return false;
		restaurantRepo.delete(restaurant);
		return true;
	}

	public Restaurant submitRestaurant(Restaurant submission) {
		String name = submission.getName();
		submission = restaurantRepo.findByRestaurantId(submission.getRestaurantId());

		if (submission == null) {
			submission = restaurantRepo.findByName(name); 
			if (submission == null)
				return null;
		}
		if (!verification.checkForFinished(submission)) {
			return null;
		}

		submission.getManager().setIsActive(true);
		submission.setManager(managerRepo.save(submission.getManager()));
		return submission;
	}
	
	public Restaurant startRestaurant(Restaurant startingRestaurant) {
		if (!verification.checkManager(startingRestaurant.getManager())) {
			startingRestaurant.setManager(generateManager(startingRestaurant));
		}
		startingRestaurant.getManager().setIsActive(false);
		if (!verification.checkBasics(startingRestaurant))
			return null;
		if(!verification.checkGenres(startingRestaurant))
			return null;
		
		return restaurantRepo.save(startingRestaurant);
	}

	private Manager generateManager(Restaurant restaurant) {
		Manager manager = new Manager();
		String[] nameParts;
		if (restaurant.getName() == null)
			nameParts = new String[] {"restaurant"};
		else
			nameParts = restaurant.getName().split(" ");
		StringBuffer name = new StringBuffer();
		for (int i = 0; i < nameParts.length && i < 3; i++)
			name.append(nameParts[i] + "-");
		manager.setUsername(name.toString() + "manager");
		name.deleteCharAt(name.length()-1);
		manager.setFirstName(name.toString());
		manager.setLastName("manager");
		manager.setPassword(encoder.encode(name));
		manager.setEmail(name + "@stacklunch.com");
		manager.setPhone("000-000-0000");
		manager.setRole(roleRepo.findRoleByName("restaurant"));
		return manager;
	}
}
