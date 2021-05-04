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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inthebytes.restaurantmanager.service.RestaurantService;

@RestController
@RequestMapping("/apis/restaurant")
@CrossOrigin("http://localhost:4200")
public class RestaurantController {

	@Autowired
	RestaurantService service;
	
	@GetMapping(value = "")
	public ResponseEntity<List<RestaurantDTO>> getRestaurants(
			@RequestParam(value = "page-size") Integer pageSize,
			@RequestParam(value = "page") Integer pageNum) {
		List<List<RestaurantDTO>> restaurants = service.getRestaurantPages(pageSize);
		if (restaurants == null || restaurants.size() == 0)
			return ResponseEntity.noContent().build();
		else {
			HttpHeaders headers = new HttpHeaders();
			headers.set("page", Integer.toString(pageNum));
			headers.set("total-pages", Integer.toString(restaurants.size()));
			return ResponseEntity.ok().headers(headers).body(restaurants.get(pageNum-1));
		}
			
	}
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<RestaurantDTO> getRestaurantByName(@PathVariable("name") String name) {
		RestaurantDTO result = service.getRestaurant(name);
		return (result == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/{restaurantId}")
	public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable("restaurantId") Long restaurantId) {
		RestaurantDTO result = service.getRestaurant(restaurantId);
		return (result == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(result);
	}
	
	@PutMapping(value = "/{restaurantId}")
	public ResponseEntity<RestaurantDTO> updateRestaurant(@Valid @RequestBody RestaurantDTO restaurant, @PathVariable("restaurantId") Long restaurantId) {
		restaurant.setRestaurantId(restaurantId);
		RestaurantDTO result = service.updateRestaurant(restaurant);
		return (result == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(result);
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