package com.inthebytes.restaurantmanager.control;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.inthebytes.restaurantmanager.service.RestaurantManagerService;

@RestController
@RequestMapping("/apis/restaurants")
@CrossOrigin("http://localhost:4200")
public class RestaurantAccountController {
	
	@Autowired
	private RestaurantManagerService service;
	
	@PutMapping(value = "/{restaurantId}/managers")
	public ResponseEntity<RestaurantDTO> addManager(
			@PathVariable Long id, 
			@RequestBody UserDto user) {
		
		return null;
	}
	
	
	@PutMapping(value = "/{restaurantId}/managers/{userId}")
	public ResponseEntity<RestaurantDTO> addManagerById(
			@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("userId") Long userId) {
		
		return null;
	}
	
	
	@DeleteMapping(value = "/{restaurantId}/managers")
	public ResponseEntity<RestaurantDTO> deleteManager(
			@PathVariable Long id, 
			@RequestBody UserDto user) {
		return null;
	}
	
	
	@DeleteMapping(value = "/{restaurantId}/managers/{userId}")
	public ResponseEntity<RestaurantDTO> deleteManagerById(
			@PathVariable("restaurantId") Long restaurantId,
			@PathVariable("userId") Long userId) {
		
		return null;
	}
}
