package com.inthebytes.restaurantmanager.mapper;

import java.util.ArrayList;
import java.util.List;

import org.xmlunit.util.Mapper;

import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Food;
import com.inthebytes.restaurantmanager.entity.Location;
import com.inthebytes.restaurantmanager.entity.Restaurant;


public interface RestaurantMapper extends Mapper<RestaurantDTO, Restaurant> {
	
	public default Restaurant convert(RestaurantDTO dto) {
		Restaurant entity = new Restaurant();
		
		if (dto.getRestaurantId() != null)
			entity.setRestaurantId(dto.getRestaurantId());
		entity.setName(dto.getName());
		entity.setCuisine(dto.getCuisine());
		entity.setLocation(convert(dto.getLocation()));
		
		List<Food> foods = new ArrayList<Food>();
		for (FoodDTO food : dto.getFoods()) {
			Food f = convert(food);
			f.setRestaurant(entity);
			foods.add(f);
		}
		entity.setFoods(foods);
		
		return entity;
	}
	
	public default RestaurantDTO convert(Restaurant entity) {
		RestaurantDTO dto = new RestaurantDTO();
		
		dto.setRestaurantId(entity.getRestaurantId());
		dto.setName(entity.getName());
		dto.setCuisine(entity.getCuisine());
		dto.setLocation(convert(entity.getLocation()));
		
		List<FoodDTO> foods = new ArrayList<FoodDTO>();
		for (Food food : entity.getFoods()) {
			FoodDTO f = convert(food);
			f.setRestaurant(dto);
			foods.add(f);
		}
		dto.setFoods(foods);
		
		return dto;
	}
	
	public default Location convert(LocationDTO dto) {
		Location entity = new Location();
		
		if (dto.getLocationId() != null)
			entity.setLocationId(dto.getLocationId());
		entity.setUnit(dto.getUnit());
		entity.setStreet(dto.getStreet());
		entity.setCity(dto.getCity());
		entity.setState(dto.getState());
		entity.setZipCode(dto.getZipCode());
		
		return entity;
	}
	
	public default LocationDTO convert(Location entity) {
		LocationDTO dto = new LocationDTO();
		
		dto.setLocationId(entity.getLocationId());
		dto.setUnit(entity.getUnit());
		dto.setStreet(entity.getStreet());
		dto.setCity(entity.getCity());
		dto.setState(entity.getState());
		dto.setZipCode(entity.getZipCode());
		
		return dto;
	}
	
	public default Food convert(FoodDTO dto) {
		Food entity = new Food();
		
		if (dto.getFoodId() != null)
			entity.setFoodId(dto.getFoodId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		
		return entity;
	}
	
	public default FoodDTO convert(Food entity) {
		FoodDTO dto = new FoodDTO();
		
		dto.setFoodId(entity.getFoodId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		
		return dto;
	}

}
