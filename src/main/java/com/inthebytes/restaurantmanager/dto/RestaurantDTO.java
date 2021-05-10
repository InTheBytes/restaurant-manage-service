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
	
	@Nullable
	private List<UserDto> managers;

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

	public List<UserDto> getManagers() {
		return managers;
	}

	public void setManagers(List<UserDto> managers) {
		this.managers = managers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((restaurantId == null) ? 0 : restaurantId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestaurantDTO other = (RestaurantDTO) obj;
		if (restaurantId == null) {
			if (other.restaurantId != null)
				return false;
		} else if (!restaurantId.equals(other.restaurantId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RestaurantDTO [name=" + name + ", cuisine=" + cuisine + ", location=" + location + ", foods=" + foods
				+ "]";
	}
}
