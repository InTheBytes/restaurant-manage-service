package com.inthebytes.restaurantmanager.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.restaurantmanager.service.RestaurantAccountService;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.role.RoleDto;
import com.inthebytes.stacklunch.data.user.UserDto;

@WebMvcTest(controllers = RestaurantAccountController.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class RestaurantAccountControllerTest {
	
	@MockBean
	RestaurantAccountService service;
	
	@Autowired
	MockMvc mock;
	
	private UserDto manager;
	
	private void makeUser() {
		UserDto user = new UserDto();
		user.setUserId("1");
		user.setUsername("TestUser");
		user.setActive(true);
		RoleDto role = new RoleDto();
		role.setRoleId(1);
		role.setName("test");
		user.setRole(role);
		manager = user;
	}
	
	private LocationDto makeLocation() {
		LocationDto location = new LocationDto();
		location.setUnit("Test");
		location.setStreet("Test");
		location.setCity("Test");
		location.setState("Test");
		location.setZipCode(11111);
		return location;
	}
	
	private RestaurantDto makeRestaurant() {
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setName("Test");
		restaurant.setCuisine("Test");
		restaurant.setLocation(makeLocation());
		return restaurant;
	}
	
	private RestaurantDto makeRestaurant(Boolean addManager) {
		RestaurantDto restaurant = makeRestaurant();
		restaurant.setFoods(new ArrayList<FoodDto>());
		restaurant.setRestaurantId((addManager) ? "1" : "2");
		Set<UserDto> managers = new HashSet<UserDto>();
		if (addManager)
			managers.add(manager);
		restaurant.setManager(managers);
		return restaurant;
		
	}
	
	@BeforeAll
	public void setUp() {
		makeUser();
	}
	
	@Test
	public void addManagerTest() throws Exception {
		Mockito.when(service.addManager("1", manager)).thenReturn(makeRestaurant(true));
		
		mock.perform(put("/restaurant/1/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("1"))
		.andExpect(jsonPath("$.managers").isNotEmpty());
	}
	
	@Test
	public void addManagerNotFoundTest() throws Exception {
		mock.perform(put("/restaurant/2/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void addManagerByIdTest() throws Exception {
		Mockito.when(service.addManager("1", "1")).thenReturn(makeRestaurant(true));
		
		mock.perform(put("/restaurant/1/manager/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("1"))
		.andExpect(jsonPath("$.managers").isNotEmpty());
	}
	
	@Test
	public void addManagerByIdNotFoundTest() throws Exception {
		mock.perform(put("/restaurant/2/manager/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void deleteManagerTest() throws Exception {
		Mockito.when(service.removeManager("2", manager)).thenReturn(makeRestaurant(false));
		
		mock.perform(delete("/restaurant/2/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("2"))
		.andExpect(jsonPath("$.managers").isEmpty());
	}
	
	@Test
	public void deleteManagerNotFoundTest() throws Exception {
		mock.perform(delete("/restaurant/2/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void deleteManagerByIdTest() throws Exception {
		Mockito.when(service.removeManager("2", "1")).thenReturn(makeRestaurant(false));
		
		mock.perform(delete("/restaurant/2/manager/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("2"))
		.andExpect(jsonPath("$.managers").isEmpty());
	}
	
	@Test
	public void deleteManagerByIdNotFoundTest() throws Exception {
		mock.perform(delete("/restaurant/2/manager/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
