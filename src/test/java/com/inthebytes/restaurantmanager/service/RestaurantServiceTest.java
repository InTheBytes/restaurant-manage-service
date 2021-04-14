package com.inthebytes.restaurantmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.persistence.EntityExistsException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Location;
import com.inthebytes.restaurantmanager.entity.Restaurant;

public class RestaurantServiceTest {

	@Autowired
	private MockMvc mock;
	
	@Mock
	RestaurantDTO repo;
	
	@InjectMocks
	RestaurantService service;

	@Test
	public void createRestaurantTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		Restaurant result = restaurant;
		result.setRestaurantId(22L);
		
		when(repo.findByName(restaurant.getName())).thenReturn(null);
		when(repo.save(restaurant)).thenReturn(result);
		
		Restaurant created = service.createRestaurant(restaurant);
		assertThat(created.getName()).isSameAs(restaurant.getName());
		assertThat(created.getRestaurantId()).isSameAs(22L);
	}

	@Test
	public void createInvalidRestaurantTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		
		when(repo.findByName(restaurant.getName())).thenThrow(new NullPointerException());
		
		assertThatThrownBy(() -> service.createRestaurant(restaurant)).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void createExistingRestaurantTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		
		when(repo.findByName(restaurant.getName())).thenReturn(restaurant);
		
		assertThatThrownBy(() -> service.createRestaurant(restaurant)).isInstanceOf(EntityExistsException.class);
	}
	
	private Restaurant makeRestaurantModel() {
		Restaurant test = new Restaurant();
		test.setName("Lexi's Burgers");

		Location location = new Location();
		location.setStreet("Main St.");
		location.setUnit("123");
		location.setCity("Sacramento");
		location.setState("California");
		location.setZipCode(95838);
		test.setLocation(location);

		test.setCuisine("Fast Food");

		return test;
	}
}
