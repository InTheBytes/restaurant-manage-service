package com.inthebytes.restaurantmanager.control;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.entity.RestaurantModel;

@RestController
@RequestMapping("/creator/restaurant")
public class RestaurantCreationController {
	
	@PostMapping(value = "/confirmation")
	public RestaurantModel addRestaurant(@RequestBody RestaurantModel restaurant) {
		return null;
	}
	
	@PostMapping(value = "/starter")
	public RestaurantModel startRestaurantCreation(@RequestBody RestaurantModel restaurant) {
		return null;
	}
	
	@PutMapping(value = "/{restaurantId}")
	public RestaurantModel updateRestaurantCreation(@RequestBody RestaurantModel restaurant) {
		return null;
	}
	
	@GetMapping("/preview/{restaurantId}")
	public RestaurantModel viewRestaurantCreation(@PathVariable("restaurantId") Long restaurantId) {
		return null;
	}
	
	@DeleteMapping(value = "/{restaurantId}")
	public RestaurantModel cancelRestaurantCreation() {
		return null;
	}
}