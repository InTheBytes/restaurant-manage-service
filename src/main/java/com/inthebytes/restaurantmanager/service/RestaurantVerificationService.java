package com.inthebytes.restaurantmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.HoursDTO;
import com.inthebytes.restaurantmanager.entity.FoodModel;
import com.inthebytes.restaurantmanager.entity.HoursModel;
import com.inthebytes.restaurantmanager.entity.LocationModel;
import com.inthebytes.restaurantmanager.entity.ManagerModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;
import com.inthebytes.restaurantmanager.entity.RestaurantModel;

@Service
public class RestaurantVerificationService {

	@Autowired
	private HoursDTO hoursRepo;

	public Boolean checkForFinished(RestaurantModel candidate) {
		if (!checkBasics(candidate))
			return false;
		if (candidate.getMenus() == null)
			return false;
		return checkListContents(candidate);
	}
	
	public Boolean checkBasics(RestaurantModel candidate) {
		if (candidate.getName() == null)
			return false;
		if (candidate.getLocations() == null)
			return false;
		if (candidate.getGenres() == null)
			return false;
		if (candidate.getHours() == null)
			return false;
		if (candidate.getManager() == null)
			return false;
		return true;
	}
	
	public Boolean checkManager(ManagerModel manager) {
		if (manager == null)
			return false;
		if (manager.getFirstName() == null)
			return false;
		if (manager.getLastName() == null)
			return false;
		if (manager.getPassword() == null)
			return false;
		if (manager.getEmail() == null)
			return false;
		if (manager.getPhone() == null)
			return false;
		if (manager.getUsername() == null)
			return false;
		return true;
	}
	
	public Boolean checkListContents(RestaurantModel candidate) {
		if (!checkGenres(candidate)) {
			return false;
		}
		if (!checkLocations(candidate)) {
			return false;
		}
		if (!checkHours(candidate)) {
			return false;
		}
		if (!checkMenus(candidate)) {
			return false;
		}
		return true;
	}
	
	public Boolean checkFood(FoodModel food) {
		Boolean flag = true;
		if (food.getName() == null)
			flag = false;
		if (food.getPrice() == null)
			flag = false;
		if (food.getPrice() == null)
			flag = false;
		if (food.getDescription() == null)
			flag = false;
		return flag;
	}
	
	public Boolean checkMenu(MenuModel menu) {
		menu.getMenuItems().removeIf((x) -> x == null);
		Boolean flag = menu.getMenuItems().size() > 0;
		if (menu.getTitle() == null)
			flag = false;
		for (FoodModel food : menu.getMenuItems()) {
			if (!checkFood(food))
				flag = false;
		}
		return flag;
	}
	
	public Boolean checkMenus(RestaurantModel candidate) {
		candidate.getMenus().removeIf((x) -> x == null);
		Boolean flag = candidate.getMenus().size() > 0;
		for (MenuModel menu : candidate.getMenus()) {
			if (!checkMenu(menu))
				flag = false;
		}
		return flag;
	}

	public Boolean checkHours(RestaurantModel candidate) {
		Boolean flag = false;
		HoursModel hours = candidate.getHours();
		if (hours.getMondayOpen() != null && hours.getMondayClose() != null)
			flag = true;
		if (hours.getTuesdayOpen() != null && hours.getTuesdayClose() != null)
			flag = true;
		if (hours.getWednesdayOpen() != null && hours.getWednesdayClose() != null)
			flag = true;
		if (hours.getThursdayOpen() != null && hours.getThursdayClose() != null)
			flag = true;
		if (hours.getFridayOpen() != null && hours.getFridayClose() != null)
			flag = true;
		if (hours.getSaturdayOpen() != null && hours.getSaturdayClose() != null)
			flag = true;
		if (hours.getSundayOpen() != null && hours.getSundayClose() != null)
			flag = true;
		return flag;
	}

	public Boolean checkGenres(RestaurantModel candidate) {
		candidate.getGenres().removeIf((x) -> x == null);
		return candidate.getGenres().size() > 0;
	}
	
	public Boolean checkLocation(LocationModel location) {
		Boolean flag = true;
		if (location.getCity() == null)
			flag = false;
		if (location.getState() == null)
			flag = false;
		if (location.getStreet() == null)
			flag = false;
		if (location.getUnit() == null)
			flag = false;
		if (location.getZip() == null)
			flag = false;
		return flag;
	}

	public Boolean checkLocations(RestaurantModel candidate) {
		candidate.getLocations().removeIf((x) -> x == null);
		Boolean flag = candidate.getLocations().size() > 0;
		for (LocationModel location : candidate.getLocations()) {
			if (!checkLocation(location))
				flag = false;
		}
		return flag;
	}
}
