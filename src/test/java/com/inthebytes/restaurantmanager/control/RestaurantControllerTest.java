package com.inthebytes.restaurantmanager.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

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
	public void getAllRestaurantsTest() throws Exception {
		RestaurantDTO rest1 = makeRestaurantModel();
		RestaurantDTO rest2 = makeRestaurantModel();
		rest2.setName("A different one");
		List<RestaurantDTO> restaurants = new ArrayList<RestaurantDTO>();
		restaurants.add(rest1);
		restaurants.add(rest2);
		
		when(service.getRestaurant()).thenReturn(restaurants);
		
		mock.perform(get("/apis/restaurant")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void getAllRestaurantsEmptyTest() throws Exception {
		when(service.getRestaurant()).thenReturn(new ArrayList<RestaurantDTO>());
		
		mock.perform(get("/apis/restaurant")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void getAllRestaurantsNullTest() throws Exception {
		when(service.getRestaurant()).thenReturn(null);
		
		mock.perform(get("/apis/restaurant")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void getRestaurantByNameTest() throws Exception {
		List<RestaurantDTO> results = new ArrayList<RestaurantDTO>();
		RestaurantDTO test = makeRestaurantModel();
		test.setName("test");
		results.add(test);
		when(service.getRestaurant("test")).thenReturn(results);
		
		mock.perform(get("/apis/restaurant/name/test")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void getRestaurantByNameNotFoundTest() throws Exception {
		when(service.getRestaurant("test")).thenReturn(null);
		
		mock.perform(get("/apis/restaurant/name/test")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getRestaurantTest() throws Exception {
		RestaurantDTO result = makeRestaurantModel();
		result.setRestaurantId(22L);
		when(service.getRestaurant(22L)).thenReturn(result);
		
		mock.perform(get("/apis/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value(result.getName()));
	}
	
	@Test
	public void getRestaurantNotFoundTest() throws Exception {
		when(service.getRestaurant(22L)).thenReturn(null);
		
		mock.perform(get("/apis/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateRestaurantTest() throws Exception {
		RestaurantDTO submission = makeRestaurantModel();
		RestaurantDTO result = makeRestaurantModel();
		RestaurantDTO transit = makeRestaurantModel();
		transit.setRestaurantId(22L);
		result.setRestaurantId(22L);
		
		when(service.updateRestaurant(transit)).thenReturn(result);
		
		mock.perform(put("/apis/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(submission)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.restaurantId").value(22L));
	}
	
	@Test
	public void updateRestaurantNotFoundTest() throws JsonProcessingException, Exception {
		RestaurantDTO submission = makeRestaurantModel();
		when(service.updateRestaurant(submission)).thenReturn(null);
		
		mock.perform(put("/apis/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(submission)))
		.andExpect(status().isNotFound());
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
