package com.inthebytes.restaurantmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.inthebytes.restaurantmanager.repository.RestaurantDao;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {

	@Mock
	RestaurantDao repo;
	
	@Mock
	RestaurantMapper mapper;

	@InjectMocks
	RestaurantService service;
	
	@Test
	public void getRestaurantTest() {
		MockitoAnnotations.initMocks(this);
		Restaurant ent1 = makeRestaurantEntity();
		Restaurant ent2 = makeRestaurantEntity();
		RestaurantDTO dto1 = makeRestaurantDTO();
		RestaurantDTO dto2 = makeRestaurantDTO();
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants.add(ent1);
		restaurants.add(ent2);
		
		when(repo.findAll()).thenReturn(restaurants);
		when(mapper.convert(ent1)).thenReturn(dto1);
		when(mapper.convert(ent2)).thenReturn(dto2);
		
		List<RestaurantDTO> results = service.getRestaurant();
		assertThat(dto1).isIn(results);
		assertThat(dto2).isIn(results);
	}
	
	@Test
	public void getRestaurantEmptyTest() {
		MockitoAnnotations.initMocks(this);
		when(repo.findAll()).thenReturn(new ArrayList<Restaurant>());
		
		List<RestaurantDTO> results = service.getRestaurant();
		assertThat(results).hasSize(0);
	}
	
	@Test
	public void getRestaurantPagesTest() {
		MockitoAnnotations.initMocks(this);
		Restaurant ent1 = makeRestaurantEntity();
		Restaurant ent2 = makeRestaurantEntity();
		RestaurantDTO dto1 = makeRestaurantDTO();
		RestaurantDTO dto2 = makeRestaurantDTO();
//		LocationDTO location = new LocationDTO("Main St.", "123", "Sacramento", "California", 11111);
//		RestaurantDTO dto2 = new RestaurantDTO("Second Test", "Fast Food", location);
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		ent2.setName("SecondTest");
		dto2.setName("SecondTest");
		dto2.setRestaurantId("33");
		
		restaurants.add(ent1);
		restaurants.add(ent2);
		when(repo.findAll()).thenReturn(restaurants);
		when(mapper.convert(ent1)).thenReturn(dto1);
		when(mapper.convert(ent2)).thenReturn(dto2);
		
		List<List<RestaurantDTO>> resultPages = service.getRestaurantPages(1);
		assertThat(resultPages).hasSize(2);
		assertThat(resultPages.get(0)).hasSize(1);
		assertThat(resultPages.get(0)).hasSize(1);
	}
	
	@Test
	public void getRestaurantWithIdTest() {
		MockitoAnnotations.initMocks(this);
		Restaurant entity = makeRestaurantEntity();
		RestaurantDTO dto = makeRestaurantDTO();
		
		when(repo.findByRestaurantId("22")).thenReturn(entity);
		when(mapper.convert(entity)).thenReturn(dto);
		RestaurantDTO result = service.getRestaurant("22");
		assertThat(result).isEqualTo(dto);
	}
	
	@Test
	public void getRestaurantWithIdNotFoundTest() {
		MockitoAnnotations.initMocks(this);
		when(repo.findByRestaurantId("22")).thenReturn(null);
		
		RestaurantDTO result = service.getRestaurant("22");
		assertThat(result).isNull();
	}
	
	@Test
	public void getRestaurantWithNameTest() {
		MockitoAnnotations.initMocks(this);
		Restaurant entity = makeRestaurantEntity();
		RestaurantDTO dto = makeRestaurantDTO();
		dto.setName("test");
		entity.setName("test");
		
		when(repo.findByName("test")).thenReturn(entity);
		when(mapper.convert(entity)).thenReturn(dto);
		RestaurantDTO result = service.getRestaurantByName("test");
		assertThat(result).isEqualTo(dto);
	}
	
	@Test
	public void getRestaurantWithNameNotFoundTest() {
		MockitoAnnotations.initMocks(this);
		when(repo.findByName("test")).thenReturn(null);
		
		RestaurantDTO result = service.getRestaurantByName("test");
		assertThat(result).isNull();
	}
	
	@Test
	public void updateRestaurantTest() {
		MockitoAnnotations.initMocks(this);
		RestaurantDTO submission = makeRestaurantDTO();
		Restaurant entity = makeRestaurantEntity();
		submission.setRestaurantId("22");
		
		when(repo.findByRestaurantId("22")).thenReturn(entity);
		when(repo.save(entity)).thenReturn(entity);
		when(mapper.convert(submission)).thenReturn(entity);
		when(mapper.convert(entity)).thenReturn(submission);
		
		RestaurantDTO result = service.updateRestaurant(submission);
		assertThat(result).isEqualTo(submission);
	}
	
	@Test
	public void updateRestaurantNotFoundTest() {
		MockitoAnnotations.initMocks(this);
		RestaurantDTO submission = makeRestaurantDTO();
		submission.setRestaurantId("22");
		when(repo.findByRestaurantId("22")).thenReturn(null);
		
		RestaurantDTO result = service.updateRestaurant(submission);
		assertThat(result).isNull();
	}

	@Test
	public void createRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDTO dto = makeRestaurantDTO();
		RestaurantDTO returnedDto = dto;
		Restaurant entity = makeRestaurantEntity();
		Restaurant result = entity;
		result.setRestaurantId("22");
		returnedDto.setRestaurantId("22");
		
		MockitoAnnotations.initMocks(this);

		when(mapper.convert(dto)).thenReturn(entity);
		when(mapper.convert(result)).thenReturn(dto);
		when(repo.findByName(dto.getName())).thenReturn(null);
		when(repo.save(entity)).thenReturn(result);

		RestaurantDTO created = service.createRestaurant(dto);
		assertThat(created.getName()).isSameAs(dto.getName());
		assertThat(created.getRestaurantId()).isSameAs("22");
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

		when(repo.findByRestaurantId("22")).thenReturn(new Restaurant());

		assertTrue(service.deleteRestaurant("22"));
	}

	@Test
	public void deleteNonexistentRestaurantTest() {
		MockitoAnnotations.initMocks(this);

		when(repo.findByRestaurantId("22")).thenReturn(null);

		assertFalse(service.deleteRestaurant("22"));
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
