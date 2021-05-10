package com.inthebytes.restaurantmanager.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.UserDto;
import com.inthebytes.restaurantmanager.entity.Food;
import com.inthebytes.restaurantmanager.entity.Location;
import com.inthebytes.restaurantmanager.entity.Restaurant;

@Component
public class RestaurantMapper {
	
	@Autowired
	UserMapper mapper;
	
	public Restaurant convert(RestaurantDTO dto) {
		Restaurant entity = new Restaurant();
		
		if (dto.getRestaurantId() != null)
			entity.setRestaurantId(dto.getRestaurantId());
		entity.setName(dto.getName());
		entity.setCuisine(dto.getCuisine());
		entity.setLocation(convert(dto.getLocation()));
		
		List<Food> foods = new ArrayList<Food>();
		if (dto.getFoods() == null)
			return entity;
		for (FoodDTO food : dto.getFoods()) {
			Food f = convert(food);
			f.setRestaurant(entity);
			foods.add(f);
		}
		entity.setFoods(foods);
		
		return entity;
	}
	
	public RestaurantDTO convert(Restaurant entity) {
		RestaurantDTO dto = new RestaurantDTO(
				entity.getName(), 
				entity.getCuisine(), 
				convert(entity.getLocation()));
		
		dto.setRestaurantId(entity.getRestaurantId());
		
		if (entity.getFoods() == null)
			return dto;
		
		List<FoodDTO> foods = new ArrayList<FoodDTO>();
		entity.getFoods().stream().forEach((x) -> {
			FoodDTO food = convert(x);
			food.setRestaurant(dto);
			foods.add(food);
		});
		dto.setFoods(foods);
		
		List<UserDto> managers = new ArrayList<UserDto>();
		entity.getManager().stream().forEach((x) -> {
			UserDto manager = mapper.convert(x);
			managers.add(manager);
		});
		dto.setManagers(managers);
		
		return dto;
	}
	
	public Location convert(LocationDTO dto) {
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
	
	public LocationDTO convert(Location entity) {
		LocationDTO dto = new LocationDTO(
				entity.getStreet(), 
				entity.getUnit(), 
				entity.getCity(), 
				entity.getState(), 
				entity.getZipCode());
		
		dto.setLocationId(entity.getLocationId());
		
		return dto;
	}
	
	public Food convert(FoodDTO dto) {
		Food entity = new Food();
		
		if (dto.getFoodId() != null)
			entity.setFoodId(dto.getFoodId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		
		return entity;
	}
	
	public FoodDTO convert(Food entity) {
		FoodDTO dto = new FoodDTO(
				entity.getName(), 
				entity.getPrice(), 
				entity.getDescription());
		
		dto.setFoodId(entity.getFoodId());
		
		return dto;
	}

}
