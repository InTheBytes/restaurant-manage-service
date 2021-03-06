package com.inthebytes.restaurantmanager.control;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.restaurantmanager.service.RestaurantAccountService;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.user.UserDto;

@RestController
@RequestMapping("/restaurant")
@Tag(name = "restaurant", description = "The restaurant manage API")
public class RestaurantAccountController {
	
	@Autowired
	private RestaurantAccountService service;
	
	private ResponseEntity<RestaurantDto> buildResponse(RestaurantDto restaurant) {
		if (restaurant == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(restaurant);
	}

	@Operation(summary = "Add manager to restaurant by User Object", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@PutMapping(value = "/{restaurantId}/manager")
	public ResponseEntity<RestaurantDto> addManager(
			@PathVariable("restaurantId") String id, 
			@Valid @RequestBody UserDto user) {
		
		RestaurantDto restaurant = service.addManager(id, user);
		return buildResponse(restaurant);
	}

	@Operation(summary = "Add manager to restaurant by User ID", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@PutMapping(value = "/{restaurantId}/manager/{userId}")
	public ResponseEntity<RestaurantDto> addManagerById(
			@PathVariable("restaurantId") String restaurantId,
			@PathVariable("userId") String userId) {
		
		RestaurantDto restaurant = service.addManager(restaurantId, userId);
		return buildResponse(restaurant);
	}

	@Operation(summary = "Delete manager from restaurant User Object", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@DeleteMapping(value = "/{restaurantId}/manager")
	public ResponseEntity<RestaurantDto> deleteManager(
			@PathVariable String restaurantId, 
			@Valid @RequestBody UserDto user) {
		
		RestaurantDto restaurant = service.removeManager(restaurantId, user);
		return buildResponse(restaurant);
	}

	@Operation(summary = "Delete manager from restaurant by User ID", description = "", tags = { "restaurant" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDto.class))
			}),
			@ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
	})
	@DeleteMapping(value = "/{restaurantId}/manager/{userId}")
	public ResponseEntity<RestaurantDto> deleteManagerById(
			@PathVariable("restaurantId") String restaurantId,
			@PathVariable("userId") String userId) {
		
		RestaurantDto restaurant = service.removeManager(restaurantId, userId);
		return buildResponse(restaurant);
	}
}
