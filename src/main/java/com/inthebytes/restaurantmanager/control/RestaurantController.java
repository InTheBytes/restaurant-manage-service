package com.inthebytes.restaurantmanager.control;

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
		Restaurant result;
		try {
			result = service.startRestaurant(restaurant);
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
		if (result != null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Restaurant-ID", Long.toString(result.getRestaurantId()));
			return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}
}