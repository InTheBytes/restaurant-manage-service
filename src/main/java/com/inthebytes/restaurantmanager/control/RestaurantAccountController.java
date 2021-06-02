package com.inthebytes.restaurantmanager.control;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.UserDto;
import com.inthebytes.restaurantmanager.service.RestaurantAccountService;

@RestController
@RequestMapping("/apis/restaurants")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000",
		"http://stacklunch.com", "http://admin.stacklunch.com", 
		"http://driver.stacklunch.com", "http://manager.stacklunch.com"})
public class RestaurantAccountController {
	
	@Autowired
	private RestaurantAccountService service;
	
	private ResponseEntity<RestaurantDTO> buildResponse(RestaurantDTO restaurant) {
		if (restaurant == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(restaurant);
	}
	
	@PutMapping(value = "/{restaurantId}/managers")
	public ResponseEntity<RestaurantDTO> addManager(
			@PathVariable("restaurantId") String id, 
			@Valid @RequestBody UserDto user) {
		
		RestaurantDTO restaurant = service.addManager(id, user);
		return buildResponse(restaurant);
	}
	
	
	@PutMapping(value = "/{restaurantId}/managers/{userId}")
	public ResponseEntity<RestaurantDTO> addManagerById(
			@PathVariable("restaurantId") String restaurantId,
			@PathVariable("userId") String userId) {
		
		RestaurantDTO restaurant = service.addManager(restaurantId, userId);
		return buildResponse(restaurant);
	}
	
	
	@DeleteMapping(value = "/{restaurantId}/managers")
	public ResponseEntity<RestaurantDTO> deleteManager(
			@PathVariable String restaurantId, 
			@Valid @RequestBody UserDto user) {
		
		RestaurantDTO restaurant = service.removeManager(restaurantId, user);
		return buildResponse(restaurant);
	}
	
	
	@DeleteMapping(value = "/{restaurantId}/managers/{userId}")
	public ResponseEntity<RestaurantDTO> deleteManagerById(
			@PathVariable("restaurantId") String restaurantId,
			@PathVariable("userId") String userId) {
		
		RestaurantDTO restaurant = service.removeManager(restaurantId, userId);
		return buildResponse(restaurant);
	}
}
