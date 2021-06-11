package com.inthebytes.restaurantmanager.control;

import java.util.List;

import javax.persistence.EntityExistsException;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@Tag(name = "restaurant", description = "The restaurant manage API")
public class RestaurantController {

	@Autowired
	RestaurantService service;

	@Operation(summary = "Get all restaurants", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class, type = "List")),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class, type = "List"))
			}),
			@ApiResponse(responseCode = "204", description = "No restaurant were found", content = @Content)
	})
	@GetMapping(value = "")
	public ResponseEntity<Page<RestaurantDTO>> getRestaurants(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "page-size", required = false, defaultValue = "10") Integer pageSize
			) {
		Page<RestaurantDTO> restaurants = service.getRestaurantPages(page, pageSize);
		if (restaurants == null || restaurants.getContent().size() == 0)
			return ResponseEntity.noContent().build();
		else {
			return ResponseEntity.ok().body(restaurants);
		}
			
	}

	@Operation(summary = "Get a restaurant by name", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class))
			}),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<RestaurantDTO> getRestaurantByName(@PathVariable("name") String name) {
		RestaurantDTO result = service.getRestaurantByName(name);
		return (result == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(result);
	}

	@Operation(summary = "Get a restaurant by ID", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class))
			}),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@GetMapping(value = "/{restaurantId}")
	public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable("restaurantId") String restaurantId) {
		RestaurantDTO result = service.getRestaurant(restaurantId);
		return (result == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(result);
	}

	@Operation(summary = "Update a restaurant by ID", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class))
			}),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@PutMapping(value = "/{restaurantId}")
	public ResponseEntity<RestaurantDTO> updateRestaurant(@Valid @RequestBody RestaurantDTO restaurant, @PathVariable("restaurantId") String restaurantId) {
		restaurant.setRestaurantId(restaurantId);
		RestaurantDTO result = service.updateRestaurant(restaurant);
		return (result == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(result);
	}

	@Operation(summary = "Create a restaurant", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class))
			}),
			@ApiResponse(responseCode = "409", description = "Restaurant could not be created", content = @Content)
	})
	@PostMapping(value = "")
	public ResponseEntity<RestaurantDTO> startRestaurantCreation(@Valid @RequestBody RestaurantDTO restaurant) {
		try {
			RestaurantDTO result = service.createRestaurant(restaurant);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Location", result.getRestaurantId());
			return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(result);
		}
		catch (EntityExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@Operation(summary = "Delete a restaurant by ID", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@DeleteMapping(value = "/{restaurantId}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable("restaurantId") String restaurantId) {
		if (service.deleteRestaurant(restaurantId)) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}