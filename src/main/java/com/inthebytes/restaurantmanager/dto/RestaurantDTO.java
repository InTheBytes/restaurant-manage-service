package com.inthebytes.restaurantmanager.dto;

import java.util.List;

import javax.persistence.Id;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class RestaurantDTO {
	
	public RestaurantDTO(String name, String cuisine, LocationDTO location) {
		super();
		this.name = name;
		this.cuisine = cuisine;
		this.location = location;
	}

	@Id
	@Nullable
	private Long restaurantId;

	@NonNull
	private String name;

	@NonNull
	private String cuisine;
	
	@NonNull
	private LocationDTO location;

	@Nullable
	private List<FoodDTO> foods;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public List<FoodDTO> getFoods() {
		return foods;
	}

	public void setFoods(List<FoodDTO> foods) {
		this.foods = foods;
	}
}
