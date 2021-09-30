package com.inthebytes.restaurantmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {

	@Mock
	RestaurantDao repo;

	@InjectMocks
	RestaurantService service;
	
	@Test
	public void getRestaurantPagesTest() {
		MockitoAnnotations.initMocks(this);
		Restaurant ent1 = makeRestaurantEntity();
		Restaurant ent2 = makeRestaurantEntity();
		RestaurantDto dto1 = makeRestaurantDto();
		RestaurantDto dto2 = makeRestaurantDto();
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		ent2.setName("SecondTest");
		dto2.setName("SecondTest");
		dto2.setRestaurantId("33");
		
		restaurants.add(ent1);
		restaurants.add(ent2);
		Page<Restaurant> page = new PageImpl<Restaurant>(restaurants);
		when(repo.findAll(PageRequest.of(0, 2))).thenReturn(page);
		when(RestaurantDto.convert(ent1)).thenReturn(dto1);
		when(RestaurantDto.convert(ent2)).thenReturn(dto2);
		
		Page<RestaurantDto> resultPages = service.getRestaurantPages(0, 2);
		assertThat(resultPages).hasSize(2);
	}
	
	@Test
	public void getRestaurantWithIdTest() {
		MockitoAnnotations.initMocks(this);
		Restaurant entity = makeRestaurantEntity();
		RestaurantDto dto = makeRestaurantDto();
		
		when(repo.findByRestaurantId("22")).thenReturn(entity);
		when(RestaurantDto.convert(entity)).thenReturn(dto);
		RestaurantDto result = service.getRestaurant("22");
		assertThat(result).isEqualTo(dto);
	}
	
	@Test
	public void getRestaurantWithIdNotFoundTest() {
		MockitoAnnotations.initMocks(this);
		when(repo.findByRestaurantId("22")).thenReturn(null);
		
		RestaurantDto result = service.getRestaurant("22");
		assertThat(result).isNull();
	}
	
	@Test
	public void getRestaurantWithNameTest() {
		MockitoAnnotations.initMocks(this);
		Restaurant entity = makeRestaurantEntity();
		RestaurantDto dto = makeRestaurantDto();
		dto.setName("test");
		entity.setName("test");
		
		when(repo.findByName("test")).thenReturn(entity);
		when(RestaurantDto.convert(entity)).thenReturn(dto);
		RestaurantDto result = service.getRestaurantByName("test");
		assertThat(result).isEqualTo(dto);
	}
	
	@Test
	public void getRestaurantWithNameNotFoundTest() {
		MockitoAnnotations.initMocks(this);
		when(repo.findByName("test")).thenReturn(null);
		
		RestaurantDto result = service.getRestaurantByName("test");
		assertThat(result).isNull();
	}
	
	@Test
	public void updateRestaurantTest() {
		MockitoAnnotations.initMocks(this);
		RestaurantDto submission = makeRestaurantDto();
		Restaurant entity = makeRestaurantEntity();
		submission.setRestaurantId("22");
		
		when(repo.findByRestaurantId("22")).thenReturn(entity);
		when(repo.save(entity)).thenReturn(entity);
		when(submission.convert()).thenReturn(entity);
		when(RestaurantDto.convert(entity)).thenReturn(submission);
		
		RestaurantDto result = service.updateRestaurant(submission);
		assertThat(result).isEqualTo(submission);
	}
	
	@Test
	public void updateRestaurantNotFoundTest() {
		MockitoAnnotations.initMocks(this);
		RestaurantDto submission = makeRestaurantDto();
		submission.setRestaurantId("22");
		when(repo.findByRestaurantId("22")).thenReturn(null);
		
		RestaurantDto result = service.updateRestaurant(submission);
		assertThat(result).isNull();
	}

	@Test
	public void createRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDto dto = makeRestaurantDto();
		RestaurantDto returnedDto = dto;
		Restaurant entity = makeRestaurantEntity();
		Restaurant result = entity;
		result.setRestaurantId("22");
		returnedDto.setRestaurantId("22");
		
		MockitoAnnotations.initMocks(this);

		when(dto.convert()).thenReturn(entity);
		when(RestaurantDto.convert(result)).thenReturn(dto);
		when(repo.findByName(dto.getName())).thenReturn(null);
		when(repo.save(entity)).thenReturn(result);

		RestaurantDto created = service.createRestaurant(dto);
		assertThat(created.getName()).isSameAs(dto.getName());
		assertThat(created.getRestaurantId()).isSameAs("22");
	}

	@Test
	public void createExistingRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantDto dto = makeRestaurantDto();
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
	
	private RestaurantDto makeRestaurantDto() {
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
