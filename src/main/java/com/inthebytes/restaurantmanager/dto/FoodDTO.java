package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FoodDTO {
	
	@Id
	@Nullable
	@JsonIgnore
	private Long foodId;

	@NonNull
	private String name;
	
	@NonNull
	private Double price;
	
	@NonNull
	private String description;
	
	@Nullable
	@JsonIgnore
	private RestaurantDTO restaurant;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RestaurantDTO getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDTO restaurant) {
		this.restaurant = restaurant;
	}
}
