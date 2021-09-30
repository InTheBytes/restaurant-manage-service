package com.inthebytes.restaurantmanager.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.inthebytes.restaurantmanager.service.RestaurantService;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc
public class RestaurantControllerTest {

	@MockBean
	RestaurantService service;
	
	@Autowired
	MockMvc mock;
	
	@Test
	public void getRestaurantsTest() throws Exception {
		RestaurantDto rest1 = makeRestaurantModel();
		RestaurantDto rest2 = makeRestaurantModel();
		rest2.setName("A different one");
		rest2.setRestaurantId("30");
		
		List<RestaurantDto> restaurants = new ArrayList<RestaurantDto>();
		restaurants.add(rest1);
		restaurants.add(rest2);
		
		Page<RestaurantDto> page = new PageImpl<RestaurantDto>(restaurants);
		
		when(service.getRestaurantPages(1, 1)).thenReturn(page);
		
		mock.perform(get("/restaurant?page-size=1&page=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void getRestaurantsEmptyTest() throws Exception {

		Page<RestaurantDto> page = Page.empty();
		when(service.getRestaurantPages(1, 1)).thenReturn(page);
		
		mock.perform(get("/restaurant?page-size=1&page=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void getRestaurantsNullTest() throws Exception {
		when(service.getRestaurantPages(1, 1)).thenReturn(null);
		
		mock.perform(get("/restaurant?page-size=1&page=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void getRestaurantByNameTest() throws Exception {
		RestaurantDto result = makeRestaurantModel();
		result.setName("test");
		when(service.getRestaurantByName("test")).thenReturn(result);
		
		mock.perform(get("/restaurant/name/test")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value(result.getName()));
	}
	
	@Test
	public void getRestaurantByNameNotFoundTest() throws Exception {
		when(service.getRestaurantByName("test")).thenReturn(null);
		
		mock.perform(get("/restaurant/name/test")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getRestaurantTest() throws Exception {
		RestaurantDto result = makeRestaurantModel();
		result.setRestaurantId("22");
		when(service.getRestaurant("22")).thenReturn(result);
		
		mock.perform(get("/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value(result.getName()));
	}
	
	@Test
	public void getRestaurantNotFoundTest() throws Exception {
		when(service.getRestaurant("22")).thenReturn(null);
		
		mock.perform(get("/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateRestaurantTest() throws Exception {
		RestaurantDto submission = makeRestaurantModel();
		RestaurantDto result = makeRestaurantModel();
		RestaurantDto transit = makeRestaurantModel();
		transit.setRestaurantId("22");
		result.setRestaurantId("22");
		
		when(service.updateRestaurant(transit)).thenReturn(result);
		
		mock.perform(put("/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(submission)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("22"));
	}
	
	@Test
	public void updateRestaurantNotFoundTest() throws JsonProcessingException, Exception {
		RestaurantDto submission = makeRestaurantModel();
		when(service.updateRestaurant(submission)).thenReturn(null);
		
		mock.perform(put("/restaurant/22")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(submission)))
		.andExpect(status().isNotFound());
	}

	@Test
	public void createRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDto restaurant = makeRestaurantModel();
		RestaurantDto result = makeRestaurantModel();
		result.setRestaurantId("22");

		when(service.createRestaurant(restaurant)).thenReturn(result);

		mock.perform(post("/restaurant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("22")));
	}

	@Test
	public void createExistingRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDto restaurant = makeRestaurantModel();
		
		when(service.createRestaurant(restaurant)).thenThrow(new EntityExistsException());

		mock.perform(post("/restaurant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	public void deleteRestaurantTest() throws Exception{
		when(service.deleteRestaurant("22")).thenReturn(true);
		
		mock.perform(delete("/restaurant/22"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void deleteNonexistentRestaurantTest() throws Exception {
		when(service.deleteRestaurant("22")).thenReturn(false);
		
		mock.perform(delete("/restaurant/22"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	private RestaurantDto makeRestaurantModel() {
		LocationDto location = new LocationDto();
		location.setUnit("123");
		location.setStreet("Main St.");
		location.setCity("Sacramento");
		location.setState("California");
		location.setZipCode(11111);
		
		RestaurantDto test = new RestaurantDto();
		test.setName("Lexi's Burgers");
		test.setCuisine("Fast Food");
		test.setLocation(location);
		return test;
	}
}
