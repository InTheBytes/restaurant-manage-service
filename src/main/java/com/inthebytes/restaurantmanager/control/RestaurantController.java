package com.inthebytes.restaurantmanager.control;

import java.util.List;

import javax.persistence.EntityExistsException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inthebytes.restaurantmanager.service.RestaurantService;

@RestController
@RequestMapping("/apis/restaurant")
public class RestaurantController {

	@Autowired
	RestaurantService service;
	
	@GetMapping(value = "")
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
		List<RestaurantDTO> restaurants = service.getRestaurant();
		if (restaurants == null || restaurants.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok().body(restaurants);
	}
	
	@GetMapping(value = "/{restaurantId}")
	public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable("restaurantId") Long restaurantId) {
		RestaurantDTO result = service.getRestaurant(restaurantId);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		else
			return ResponseEntity.ok().body(result);
	}
	
	@PutMapping(value = "/{restaurantId}")
	public ResponseEntity<RestaurantDTO> updateRestaurant(@Valid @RequestBody RestaurantDTO restaurant, @PathVariable("restaurantId") Long restaurantId) {
		restaurant.setRestaurantId(restaurantId);
		RestaurantDTO result = service.updateRestaurant(restaurant);
		if (result == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok().body(result);
	}
	
	@PostMapping(value = "")
	public ResponseEntity<RestaurantDTO> startRestaurantCreation(@Valid @RequestBody RestaurantDTO restaurant) {
		try {
			RestaurantDTO result = service.createRestaurant(restaurant);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Location", Long.toString(result.getRestaurantId()));
			return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(result);
		}
		catch (EntityExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}


	@DeleteMapping(value = "/{restaurantId}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
		if (service.deleteRestaurant(restaurantId)) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}