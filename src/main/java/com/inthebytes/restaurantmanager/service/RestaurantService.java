package com.inthebytes.restaurantmanager.service;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.stacklunch.data.food.FoodDto;
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
		return RestaurantDto.convert(restaurantRepo.findAll(PageRequest.of(page, pageSize)));
	}
	
	private RestaurantDto sortRestaurantDto(Restaurant restaurant) {
		if (restaurant == null) return null;
		RestaurantDto dto = RestaurantDto.convert(restaurant);
		if (dto.getFoods() == null) return dto;
		
		Set<FoodDto> sortedFoods = new TreeSet<>((food1, food2) -> food1.getName().compareTo(food2.getName()));
		sortedFoods.addAll(dto.getFoods());
		dto.setFoods(sortedFoods);
		return dto;
	}
	

	public RestaurantDto getRestaurantByName(String name) {
		Restaurant restaurant = restaurantRepo.findByName(name);
		return sortRestaurantDto(restaurant);
	}
	
	public RestaurantDto getRestaurantByManagerID(String id) {
		Restaurant restaurant = restaurantRepo.findByManagerUserId(id);
		return sortRestaurantDto(restaurant);
	}
	
	public RestaurantDto getRestaurant(String restuarantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restuarantId);
		return sortRestaurantDto(restaurant);
	}
	
	public RestaurantDto updateRestaurant(RestaurantDto restaurant) {
		Restaurant previousRestaurant = restaurantRepo.findByRestaurantId(restaurant.getRestaurantId());
		if (previousRestaurant == null)
			return null;
	
		final Restaurant entity = restaurant.convert();
		entity.getFoods().stream().forEach(food -> food.setRestaurant(entity));
		return sortRestaurantDto(restaurantRepo.save(entity));
	}
}
