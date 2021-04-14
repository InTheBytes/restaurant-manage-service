package com.inthebytes.restaurantmanager.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.service.RestaurantService;

@RestController
@RequestMapping("/apis")
public class RestaurantController {

	@Autowired
	RestaurantService service;

	@DeleteMapping(value = "restaurant/{restaurantId}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
		if (service.delete(restaurantId)) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Deleted-Restaurant-ID", Long.toString(restaurantId));
			return ResponseEntity.ok().headers(responseHeaders).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}