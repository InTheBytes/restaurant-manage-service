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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

	@Mock
	RestaurantDao repo;

	@Autowired
	@InjectMocks
	RestaurantService service;
	
	@Test
	public void getRestaurantPagesTest() {
		Restaurant ent1 = makeRestaurantEntity();
		Restaurant ent2 = makeRestaurantEntity();
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		ent2.setName("SecondTest");
		
		restaurants.add(ent1);
		restaurants.add(ent2);
		Page<Restaurant> page = new PageImpl<Restaurant>(restaurants);
		when(repo.findAll(PageRequest.of(0, 2))).thenReturn(page);
		
		Page<RestaurantDto> resultPages = service.getRestaurantPages(0, 2);
		assertThat(resultPages).hasSize(2);
	}
	
	@Test
	public void getRestaurantWithIdTest() {
		Restaurant entity = makeRestaurantEntity();
		RestaurantDto dto = makeRestaurantDto();
		
		when(repo.findByRestaurantId("22")).thenReturn(entity);
		RestaurantDto result = service.getRestaurant("22");
		assertThat(result).isEqualTo(dto);
	}
	
	@Test
	public void getRestaurantWithIdNotFoundTest() {
		when(repo.findByRestaurantId("22")).thenReturn(null);
		
		RestaurantDto result = service.getRestaurant("22");
		assertThat(result).isNull();
	}
	
	@Test
	public void getRestaurantWithNameTest() {
		Restaurant entity = makeRestaurantEntity();
		RestaurantDto dto = makeRestaurantDto();
		dto.setName("test");
		entity.setName("test");
		
		when(repo.findByName("test")).thenReturn(entity);
		RestaurantDto result = service.getRestaurantByName("test");
		assertThat(result).isEqualTo(dto);
	}
	
	@Test
	public void getRestaurantWithNameNotFoundTest() {
		when(repo.findByName("test")).thenReturn(null);
		
		RestaurantDto result = service.getRestaurantByName("test");
		assertThat(result).isNull();
	}
	
	@Test
	public void updateRestaurantTest() {
		RestaurantDto submission = makeRestaurantDto();
		Restaurant entity = makeRestaurantEntity();
		submission.setRestaurantId("22");
		
		when(repo.findByRestaurantId("22")).thenReturn(entity);
		when(repo.save(entity)).thenReturn(entity);
		
		RestaurantDto result = service.updateRestaurant(submission);
		assertThat(result).isEqualTo(submission);
	}
	
	@Test
	public void updateRestaurantNotFoundTest() {
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

		when(repo.findByName(dto.getName())).thenReturn(entity);

		assertThatThrownBy(() -> service.createRestaurant(dto)).isInstanceOf(EntityExistsException.class);
	}

	public void deleteRestaurantTest() {

		when(repo.findByRestaurantId("22")).thenReturn(new Restaurant());

		assertTrue(service.deleteRestaurant("22"));
	}

	@Test
	public void deleteNonexistentRestaurantTest() {

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
		test.setRestaurantId("22");
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
		location.setZipCode(11111);
		test.setLocation(location);

		test.setCuisine("Fast Food");

		test.setRestaurantId("22");
		return test;
	}
}
