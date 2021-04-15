package com.inthebytes.restaurantmanager.mapper;

import org.xmlunit.util.Mapper;

import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Food;
import com.inthebytes.restaurantmanager.entity.Location;
import com.inthebytes.restaurantmanager.entity.Restaurant;


public interface RestaurantMapper extends Mapper<RestaurantDTO, Restaurant> {
	
	public default Restaurant convert(RestaurantDTO dto) {
		return null;
	}
	
	public default RestaurantDTO convert(Restaurant entity) {
		return null;
	}
	
	public default Location convert(LocationDTO dto) {
		return null;
	}
	
	public default LocationDTO convert(Location entity) {
		return null;
	}
	
	public default Food convert(FoodDTO dto) {
		return null;
	}
	
	public default FoodDTO convert(Food entity) {
		return null;
	}

}
