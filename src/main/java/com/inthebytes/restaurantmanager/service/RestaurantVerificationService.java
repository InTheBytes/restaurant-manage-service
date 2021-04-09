package com.inthebytes.restaurantmanager.service;

import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.entity.Manager;
import com.inthebytes.restaurantmanager.entity.Restaurant;

@Service
public class RestaurantVerificationService {


	public Boolean checkForFinished(Restaurant candidate) {
		if (!checkBasics(candidate))
			return false;
		return checkListContents(candidate);
	}
	
	public Boolean checkBasics(Restaurant candidate) {
		if (candidate.getName() == null)
			return false;
		if (candidate.getGenres() == null)
			return false;
		if (candidate.getManager() == null)
			return false;
		return true;
	}
	
	public Boolean checkManager(Manager manager) {
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
	
	public Boolean checkListContents(Restaurant candidate) {
		if (!checkGenres(candidate)) {
			return false;
		}
		return true;
	}

	public Boolean checkGenres(Restaurant candidate) {
		candidate.getGenres().removeIf((x) -> x == null);
		return candidate.getGenres().size() > 0;
	}
}
