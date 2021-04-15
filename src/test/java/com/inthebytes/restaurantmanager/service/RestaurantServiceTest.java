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
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Location;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.mapper.RestaurantMapper;
import com.inthebytes.restaurantmanager.repository.RestaurantRepository;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {

	@Mock
	RestaurantRepository repo;
	
	@Mock
	RestaurantMapper mapper;

	@InjectMocks
	RestaurantService service;

	@Test
	public void createRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDTO dto = makeRestaurantDTO();
		RestaurantDTO returnedDto = dto;
		Restaurant entity = makeRestaurantEntity();
		Restaurant result = entity;
		result.setRestaurantId(22L);
		returnedDto.setRestaurantId(22L);
		
		MockitoAnnotations.initMocks(this);

		when(mapper.convert(dto)).thenReturn(entity);
		when(mapper.convert(result)).thenReturn(dto);
		when(repo.findByName(dto.getName())).thenReturn(null);
		when(repo.save(entity)).thenReturn(result);

		RestaurantDTO created = service.createRestaurant(dto);
		assertThat(created.getName()).isSameAs(dto.getName());
		assertThat(created.getRestaurantId()).isSameAs(22L);
	}

	@Test
	public void createExistingRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDTO dto = makeRestaurantDTO();
		Restaurant entity = makeRestaurantEntity();
		
		MockitoAnnotations.initMocks(this);

		when(repo.findByName(dto.getName())).thenReturn(entity);

		assertThatThrownBy(() -> service.createRestaurant(dto)).isInstanceOf(EntityExistsException.class);
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
	
	private RestaurantDTO makeRestaurantDTO() {
		LocationDTO location = new LocationDTO("Main St.", "123", "Sacramento", "California", 11111);
		RestaurantDTO test = new RestaurantDTO("Lexi's Burgers", "Fast Food", location);

		return test;
	}
	
	private Restaurant makeRestaurantEntity() {
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
