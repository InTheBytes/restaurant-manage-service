package com.inthebytes.restaurantmanager.control;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDto;
import com.inthebytes.restaurantmanager.dto.UserDto;
import com.inthebytes.restaurantmanager.service.RestaurantAccountService;

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
		user.setUserId(1L);
		user.setUsername("TestUser");
		user.setIsActive(true);
		RoleDto role = new RoleDto();
		role.setRoleId(1L);
		role.setName("test");
		user.setRole(role);
		manager = user;
	}
	
	private RestaurantDTO makeRestaurant(Boolean addManager) {
		LocationDTO location = new LocationDTO("Test", "Test", "Test", "Test", 11111);
		RestaurantDTO restaurant = new RestaurantDTO("Test", "Test", location);
		restaurant.setFoods(new ArrayList<FoodDTO>());
		restaurant.setRestaurantId((addManager) ? 1L : 2L);
		List<UserDto> managers = new ArrayList<UserDto>();
		if (addManager)
			managers.add(manager);
		restaurant.setManagers(managers);
		return restaurant;
		
	}
	
	@BeforeAll
	public void setUp() {
		makeUser();
	}
	
	@Test
	public void addManagerTest() throws Exception {
		Mockito.when(service.addManager(1L, manager)).thenReturn(makeRestaurant(true));
		
		mock.perform(put("/apis/restaurants/1/managers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value(1L))
		.andExpect(jsonPath("$.managers").isNotEmpty());
	}
	
	@Test
	public void addManagerNotFoundTest() throws Exception {
		mock.perform(put("/apis/restaurants/2/managers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void addManagerByIdTest() throws Exception {
		Mockito.when(service.addManager(1L, 1L)).thenReturn(makeRestaurant(true));
		
		mock.perform(put("/apis/restaurants/1/managers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value(1L))
		.andExpect(jsonPath("$.managers").isNotEmpty());
	}
	
	@Test
	public void addManagerByIdNotFoundTest() throws Exception {
		mock.perform(put("/apis/restaurants/2/managers/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void deleteManagerTest() throws Exception {
		Mockito.when(service.removeManager(2L, manager)).thenReturn(makeRestaurant(false));
		
		mock.perform(delete("/apis/restaurants/2/managers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value(2L))
		.andExpect(jsonPath("$.managers").isEmpty());
	}
	
	@Test
	public void deleteManagerNotFoundTest() throws Exception {
		mock.perform(delete("/apis/restaurants/2/managers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(manager)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void deleteManagerByIdTest() throws Exception {
		Mockito.when(service.removeManager(2L, 1L)).thenReturn(makeRestaurant(false));
		
		mock.perform(delete("/apis/restaurants/2/managers/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value(2L))
		.andExpect(jsonPath("$.managers").isEmpty());
	}
	
	@Test
	public void deleteManagerByIdNotFoundTest() throws Exception {
		mock.perform(delete("/apis/restaurants/2/managers/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
