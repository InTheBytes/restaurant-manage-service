package com.inthebytes.restaurantmanager.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.entity.RestaurantModel;
import com.inthebytes.restaurantmanager.service.RestaurantCreationService;

@RestController
@RequestMapping("/creator/restaurant")
public class RestaurantCreationController {

	@Autowired
	RestaurantCreationService service;

	@PostMapping(value = "/confirmation")
	public ResponseEntity<RestaurantModel> addRestaurant(@RequestBody RestaurantModel restaurant) {
		RestaurantModel result = service.submitRestaurant(restaurant);
		if (result != null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Restaurant-ID", Long.toString(result.getRestaurantId()));
			return ResponseEntity.ok().headers(responseHeaders).body(result);
		} else {
			HttpStatus status = (service.isSaved(restaurant.getRestaurantId()) ? HttpStatus.NOT_ACCEPTABLE : HttpStatus.NOT_FOUND);
			return ResponseEntity.status(status).build();
		}
	}

	@PostMapping(value = "/starter")
	public ResponseEntity<RestaurantModel> startRestaurantCreation(@RequestBody RestaurantModel restaurant) {
		RestaurantModel result = service.startRestaurant(restaurant);
		if (result != null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Restaurant-ID", Long.toString(result.getRestaurantId()));
			return ResponseEntity.ok().headers(responseHeaders).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@PutMapping(value = "/{restaurantId}")
	public ResponseEntity<RestaurantModel> updateRestaurantCreation(@RequestBody RestaurantModel restaurant) {
		RestaurantModel result = service.updateRestaurant(restaurant);
		if (result != null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Restaurant-ID", Long.toString(result.getRestaurantId()));
			return ResponseEntity.ok().headers(responseHeaders).body(result);
		} else {
			HttpStatus status = (service.isSaved(restaurant.getRestaurantId()) ? HttpStatus.METHOD_NOT_ALLOWED : HttpStatus.NOT_FOUND);
			return ResponseEntity.status(status).build();
		}
	}

	@GetMapping("/preview/{restaurantId}")
	public ResponseEntity<RestaurantModel> viewRestaurantCreation(@PathVariable("restaurantId") Long restaurantId) {
		RestaurantModel restaurant = service.getRestaurantInProgress(restaurantId);
		if (restaurant != null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Restaurant-ID", Long.toString(restaurantId));
			return ResponseEntity.ok().headers(responseHeaders).body(restaurant);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping(value = "/{restaurantId}")
	public ResponseEntity cancelRestaurantCreation(@PathVariable("restaurantId") Long restaurantId) {
		Boolean flag = service.trashRestaurantProgress(restaurantId);
		if (flag) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Deleted-Restaurant-ID", Long.toString(restaurantId));
			return ResponseEntity.ok().headers(responseHeaders).build();
		}
		else {
			HttpStatus result = (service.isSaved(restaurantId) ? HttpStatus.METHOD_NOT_ALLOWED : HttpStatus.NOT_FOUND);
			return ResponseEntity.status(result).build();
		}
	}
}