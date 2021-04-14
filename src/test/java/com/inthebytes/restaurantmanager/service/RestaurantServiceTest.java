package com.inthebytes.restaurantmanager.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Restaurant;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {

	@Mock
	RestaurantDTO repo;

	@InjectMocks
	RestaurantService service;

	@Test
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
}
