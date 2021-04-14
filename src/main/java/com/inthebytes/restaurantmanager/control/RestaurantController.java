package com.inthebytes.restaurantmanager.control;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.service.RestaurantService;

@RestController
@RequestMapping("/apis")
public class RestaurantController {
	
	@Autowired
	private RestaurantService service;

	@PostMapping(value = "/restaurant")
	public ResponseEntity<Restaurant> startRestaurantCreation(@RequestBody Restaurant restaurant) {
		try {
			Restaurant result = service.createRestaurant(restaurant);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Restaurant-ID", Long.toString(result.getRestaurantId()));
			return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(result);
		} 
		catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} 
		catch (EntityExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}