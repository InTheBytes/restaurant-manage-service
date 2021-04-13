package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.inthebytes.restaurantmanager.dto.CustomizationDTO;
import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.GenreDTO;
import com.inthebytes.restaurantmanager.dto.HoursDTO;
=======
>>>>>>> manual-creation-reduction
import com.inthebytes.restaurantmanager.dto.ManagerDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDTO;
<<<<<<< HEAD
import com.inthebytes.restaurantmanager.entity.CustomizationModel;
import com.inthebytes.restaurantmanager.entity.FoodCustomization;
import com.inthebytes.restaurantmanager.entity.FoodModel;
import com.inthebytes.restaurantmanager.entity.GenreModel;
import com.inthebytes.restaurantmanager.entity.HoursModel;
import com.inthebytes.restaurantmanager.entity.ManagerModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;
import com.inthebytes.restaurantmanager.entity.RestaurantModel;
=======
import com.inthebytes.restaurantmanager.entity.Manager;
import com.inthebytes.restaurantmanager.entity.Restaurant;
>>>>>>> manual-creation-reduction

@Service
public class RestaurantCreationService {

	@Autowired
	private BCryptPasswordEncoder encoder;


	@Autowired
	private ManagerDTO managerRepo;
	@Autowired
	private RoleDTO roleRepo;
	@Autowired
<<<<<<< HEAD
	private MenuDTO menuRepo;
	@Autowired
	private GenreDTO genreRepo;
	@Autowired
=======
>>>>>>> manual-creation-reduction
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

		submission.getManager().setIsActive(true);
		submission.setManager(managerRepo.save(submission.getManager()));
		return submission;
	}
	
	public Restaurant startRestaurant(Restaurant startingRestaurant) {
		startingRestaurant.setManager(generateManager(startingRestaurant));
		
<<<<<<< HEAD
		startingRestaurant.setGenres(existingGenres(startingRestaurant));;
		
		HoursModel result = existingHours(startingRestaurant.getHours());
		if (result != null)
			startingRestaurant.setHours(result);
		else
			startingRestaurant.setHours(hoursRepo.save(startingRestaurant.getHours()));
=======
>>>>>>> manual-creation-reduction
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
<<<<<<< HEAD
	
	private List<GenreModel> existingGenres(RestaurantModel restaurant) {
		List<GenreModel> genres = new ArrayList<GenreModel>();
		for (GenreModel genre : restaurant.getGenres()) {
			GenreModel saved = genreRepo.findByGenreTitle(genre.getTitle());
			if (saved != null)
				genres.add(saved);
			else
				genres.add(genre);
		}
		return genres;
	}
	
	private HoursModel existingHours(HoursModel hours) {
		for (HoursModel storedHours : hoursRepo.findAll()) {
			if (storedHours.getMondayOpen().equals(hours.getMondayOpen()) &&
					storedHours.getMondayClose().equals(hours.getMondayClose()) &&
					storedHours.getTuesdayOpen().equals(hours.getTuesdayOpen()) &&
					storedHours.getTuesdayClose().equals(hours.getTuesdayClose()) &&
					storedHours.getWednesdayOpen().equals(hours.getWednesdayOpen()) &&
					storedHours.getWednesdayClose().equals(hours.getWednesdayClose()) &&
					storedHours.getThursdayOpen().equals(hours.getThursdayOpen()) &&
					storedHours.getThursdayClose().equals(hours.getThursdayClose()) &&
					storedHours.getFridayOpen().equals(hours.getFridayOpen()) &&
					storedHours.getFridayClose().equals(hours.getFridayClose()) &&
					storedHours.getSaturdayOpen().equals(hours.getSaturdayOpen()) &&
					storedHours.getSaturdayClose().equals(hours.getSaturdayClose()) &&
					storedHours.getSundayOpen().equals(hours.getSundayOpen()) &&
					storedHours.getSundayClose().equals(hours.getSundayClose())) {
				
				return storedHours;
			}
		}
		return null;
	}
=======
>>>>>>> manual-creation-reduction
}
