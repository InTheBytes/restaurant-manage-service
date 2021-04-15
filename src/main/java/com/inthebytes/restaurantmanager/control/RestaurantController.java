package com.inthebytes.restaurantmanager.control;

import javax.persistence.EntityExistsException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inthebytes.restaurantmanager.service.RestaurantService;

@RestController
@RequestMapping("/apis/restaurant")
public class RestaurantController {

	@Autowired
	RestaurantService service;

	@PostMapping(value = "")
	public ResponseEntity<RestaurantDTO> startRestaurantCreation(@Valid @RequestBody RestaurantDTO restaurant) {
		try {
			RestaurantDTO result = service.createRestaurant(restaurant);
			System.out.println("\n\n\n\n\n\n\n"+restaurant.getName());
			System.out.println(result.getName()+"\n\n\n\n\n\n");
			System.out.println(result.getRestaurantId()+"\n\n\n\n\n\n");
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