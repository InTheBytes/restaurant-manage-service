package com.inthebytes.restaurantmanager.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;

import com.inthebytes.restaurantmanager.service.RestaurantService;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc
public class RestaurantControllerTest {

	@MockBean
	RestaurantService service;
	
	@Autowired
	MockMvc mock;
	
	@Test
	public void getAllRestaurantsTest() {
		
	}
	
	@Test
	public void getAllRestaurantsEmptyTest() {
		
	}
	
	@Test
	public void getRestaurantTest() {
		
	}
	
	@Test
	public void getRestaurantNotFoundTest() {
		
	}
	
	@Test
	public void updateRestaurantTest() {
		
	}
	
	@Test
	public void updateRestaurantNotFoundTest() {
		
	}

	@Test
	public void createRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDTO restaurant = makeRestaurantModel();
		RestaurantDTO result = makeRestaurantModel();
		result.setRestaurantId(22L);

		when(service.createRestaurant(restaurant)).thenReturn(result);

		mock.perform(post("/apis/restaurant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("22")));
	}

	@Test
	public void createExistingRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDTO restaurant = makeRestaurantModel();
		
		when(service.createRestaurant(restaurant)).thenThrow(new EntityExistsException());

		mock.perform(post("/apis/restaurant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	public void deleteRestaurantTest() throws Exception{
		when(service.deleteRestaurant(22L)).thenReturn(true);
		
		mock.perform(delete("/apis/restaurant/22"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void deleteNonexistentRestaurantTest() throws Exception {
		when(service.deleteRestaurant(22L)).thenReturn(false);
		
		mock.perform(delete("/apis/restaurant/22"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	private RestaurantDTO makeRestaurantModel() {
		LocationDTO location = new LocationDTO("Main St.", "123", "Sacramento", "California", 11111);
		RestaurantDTO test = new RestaurantDTO("Lexi's Burgers", "Fast Food", location);

		return test;
	}
}
