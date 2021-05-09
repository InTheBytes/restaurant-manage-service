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
	public void getRestaurantsTest() throws Exception {
		RestaurantDTO rest1 = makeRestaurantModel();
		RestaurantDTO rest2 = makeRestaurantModel();
		rest2.setName("A different one");
		rest2.setRestaurantId(30L);
		List<RestaurantDTO> restaurants1 = new ArrayList<RestaurantDTO>();
		restaurants1.add(rest1);
		List<RestaurantDTO> restaurants2 = new ArrayList<RestaurantDTO>();
		restaurants2.add(rest2);
		
		List<List<RestaurantDTO>> restaurants = new ArrayList<List<RestaurantDTO>>();
		restaurants.add(restaurants1);
		restaurants.add(restaurants2);
		
		when(service.getRestaurantPages(1)).thenReturn(restaurants);
		
		mock.perform(get("/apis/restaurant?page-size=1&page=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(MockMvcResultMatchers.header().string("page", Matchers.containsString("1")))
		.andExpect(MockMvcResultMatchers.header().string("total-pages", Matchers.containsString("2")));
	}
	
	@Test
	public void getRestaurantsEmptyTest() throws Exception {
		when(service.getRestaurantPages(1)).thenReturn(new ArrayList<List<RestaurantDTO>>());
		
		mock.perform(get("/apis/restaurant?page-size=1&page=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void getRestaurantsNullTest() throws Exception {
		when(service.getRestaurantPages(1)).thenReturn(null);
		
		mock.perform(get("/apis/restaurant?page-size=1&page=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void getRestaurantByNameTest() throws Exception {
		RestaurantDTO result = makeRestaurantModel();
		result.setName("test");
		when(service.getRestaurant("test")).thenReturn(result);
		
		mock.perform(get("/apis/restaurant/name/test")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value(result.getName()));
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
