package com.inthebytes.restaurantmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inthebytes.restaurantmanager.entity.Location;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.repository.RestaurantRepository;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {

	@Mock
	RestaurantRepository repo;

	@InjectMocks
	RestaurantService service;

	@Test
	public void createRestaurantTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		Restaurant result = restaurant;
		result.setRestaurantId(22L);
		
		MockitoAnnotations.initMocks(this);

		when(repo.findByName(restaurant.getName())).thenReturn(null);
		when(repo.save(restaurant)).thenReturn(result);

		Restaurant created = service.createRestaurant(restaurant);
		assertThat(created.getName()).isSameAs(restaurant.getName());
		assertThat(created.getRestaurantId()).isSameAs(22L);
	}

	@Test
	public void createInvalidRestaurantTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		
		MockitoAnnotations.initMocks(this);

		when(repo.findByName(restaurant.getName())).thenThrow(new NullPointerException());

		assertThatThrownBy(() -> service.createRestaurant(restaurant)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void createExistingRestaurantTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		
		MockitoAnnotations.initMocks(this);

		when(repo.findByName(restaurant.getName())).thenReturn(restaurant);

		assertThatThrownBy(() -> service.createRestaurant(restaurant)).isInstanceOf(EntityExistsException.class);
	}

	public void deleteRestaurantTest() {
		MockitoAnnotations.initMocks(this);

		when(repo.findByRestaurantId(22L)).thenReturn(new Restaurant());

		assertTrue(service.deleteRestaurant(22L));
	}

	@Test
	public void deleteNonexistentRestaurantTest() {
		MockitoAnnotations.initMocks(this);

		when(repo.findByRestaurantId(22L)).thenReturn(null);

		assertFalse(service.deleteRestaurant(22L));
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
