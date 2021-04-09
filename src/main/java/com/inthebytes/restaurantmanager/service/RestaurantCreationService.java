package com.inthebytes.restaurantmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.CustomizationDTO;
import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.GenreDTO;
import com.inthebytes.restaurantmanager.dto.HoursDTO;
import com.inthebytes.restaurantmanager.dto.ManagerDTO;
import com.inthebytes.restaurantmanager.dto.MenuDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDTO;
import com.inthebytes.restaurantmanager.entity.CustomizationModel;
import com.inthebytes.restaurantmanager.entity.FoodCustomization;
import com.inthebytes.restaurantmanager.entity.FoodModel;
import com.inthebytes.restaurantmanager.entity.GenreModel;
import com.inthebytes.restaurantmanager.entity.HoursModel;
import com.inthebytes.restaurantmanager.entity.ManagerModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;
import com.inthebytes.restaurantmanager.entity.RestaurantModel;

@Service
public class RestaurantCreationService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private RestaurantVerificationService verification;

	@Autowired
	private CustomizationDTO customizationRepo;
	@Autowired
	private FoodDTO foodRepo;
	@Autowired
	private HoursDTO hoursRepo;
	@Autowired
	private ManagerDTO managerRepo;
	@Autowired
	private RoleDTO roleRepo;
	@Autowired
	private MenuDTO menuRepo;
	@Autowired
	private GenreDTO genreRepo;
	@Autowired
	private RestaurantDTO restaurantRepo;
	
	public RestaurantModel getRestaurantInProgress(Long id) {
		RestaurantModel restaurant = restaurantRepo.findByRestaurantId(id);
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
		RestaurantModel restaurant = getRestaurantInProgress(restaurantId);
		if (restaurant == null)
			return false;
		restaurantRepo.delete(restaurant);
		return true;
	}

	public RestaurantModel submitRestaurant(RestaurantModel submission) {
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
	
	public RestaurantModel startRestaurant(RestaurantModel startingRestaurant) {
		if (!verification.checkManager(startingRestaurant.getManager())) {
			startingRestaurant.setManager(generateManager(startingRestaurant));
		}
		startingRestaurant.getManager().setIsActive(false);
		if (!verification.checkBasics(startingRestaurant))
			return null;
		if(!verification.checkLocations(startingRestaurant))
			return null;
		if(!verification.checkHours(startingRestaurant))
			return null;
		if(!verification.checkGenres(startingRestaurant))
			return null;
		if (startingRestaurant.getMenus() == null)
			startingRestaurant.setMenus(new ArrayList<MenuModel>());
		
		startingRestaurant.setGenres(existingGenres(startingRestaurant));;
		
		HoursModel result = existingHours(startingRestaurant.getHours());
		if (result != null)
			startingRestaurant.setHours(result);
		else
			startingRestaurant.setHours(hoursRepo.save(startingRestaurant.getHours()));
		return restaurantRepo.save(startingRestaurant);
	}
	
	public RestaurantModel updateRestaurant(RestaurantModel restaurant) {
		if (getRestaurantInProgress(restaurant.getRestaurantId()) == null) {
			return null;
		}
		HoursModel result = existingHours(restaurant.getHours());
		if (result != null)
			restaurant.setHours(result);
		else
			restaurant.setHours(hoursRepo.save(restaurant.getHours()));
		
		updateMenus(restaurant);
		
		return restaurant;
		
	}
	
	private void updateMenus(RestaurantModel restaurant) {
		List<MenuModel> menus = restaurant.getMenus();
		menus = menus.stream()
				.map((x) -> {
					List<FoodModel> list = x.getMenuItems()
							.stream()
							.map(y -> updateFood(y)).collect(Collectors.toList());
					x.setMenuItems(list);
					return x;
				})
				.collect(Collectors.toList());
		for (MenuModel menu : menus) {
			MenuModel savedCopy = menuRepo.findByMenuId(menu.getMenuId());
			if (savedCopy == null || !menu.getTitle().equals(savedCopy.getTitle()))
				menu = menuRepo.save(menu);
			else if (menu.getTitle().equals(savedCopy.getTitle()))
				menu = savedCopy;
		}
		restaurant.setMenus(menus);
	}
	
	private FoodModel updateFood(FoodModel food) {
		List<FoodCustomization> list = food.getCustomizations()
				.stream()
				.map((x) -> {
					CustomizationModel cust = x.getCustomization();
					CustomizationModel saved = customizationRepo.findByDescription(cust.getDescription());
					if (saved != null)
						x.setCustomization(saved);
					else
						x.setCustomization(customizationRepo.save(cust));
					return x;
					})
				.collect(Collectors.toList());
		
		FoodModel savedFood = foodRepo.findByFoodId(food.getFoodId());
		if (savedFood != null && savedFood.getName().equals(food.getName())) 
			food = savedFood;
		
		food.setCustomizations(list);
		return foodRepo.save(food);
	}

	private ManagerModel generateManager(RestaurantModel restaurant) {
		ManagerModel manager = new ManagerModel();
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
}
