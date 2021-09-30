package com.inthebytes.restaurantmanager.service;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

@Service
@Transactional
public class RestaurantService {

	@Autowired
	private RestaurantDao restaurantRepo;

	public RestaurantDto createRestaurant(RestaurantDto restaurant) {
		if (restaurantRepo.findByName(restaurant.getName()) != null)
			throw new EntityExistsException();
		return RestaurantDto.convert(restaurantRepo.save(restaurant.convert()));
	}

	public Boolean deleteRestaurant(String restaurantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		if (restaurant == null)
			return false;
		restaurantRepo.delete(restaurant);
		return true;
	}
	
	public Page<RestaurantDto> getRestaurantPages(Integer page, Integer pageSize) {
		return restaurantRepo.findAll(PageRequest.of(page, pageSize)).map((x) -> RestaurantDto.convert(x));
	}
	

	public RestaurantDto getRestaurantByName(String name) {
		Restaurant restaurant = restaurantRepo.findByName(name);
		if (restaurant == null)
			return null;
		else
			return RestaurantDto.convert(restaurant);
	}
	
	public RestaurantDto getRestaurantByManagerID(String id) {
		Restaurant restaurant = restaurantRepo.findByManagerUserId(id);
		if (restaurant == null)
			return null;
		else
			return RestaurantDto.convert(restaurant);
	}
	
	public RestaurantDto getRestaurant(String restuarantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restuarantId);
		if (restaurant == null)
			return null;
		else
			return RestaurantDto.convert(restaurant);
	}
	
	public RestaurantDto updateRestaurant(RestaurantDto restaurant) {
		Restaurant restaurantEntity = restaurantRepo.findByRestaurantId(restaurant.getRestaurantId());
		if (restaurantEntity == null)
			return null;
		else {
			restaurantEntity = restaurant.convert();
			return RestaurantDto.convert(restaurantRepo.save(restaurantEntity));
		}
	}
}
