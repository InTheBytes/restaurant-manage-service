package com.inthebytes.restaurantmanager.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.restaurantmanager.RestaurantManagerTestConfig;
import com.inthebytes.restaurantmanager.service.RestaurantAccountService;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.role.RoleDto;
import com.inthebytes.stacklunch.data.user.UserDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = {RestaurantManagerTestConfig.class, RestaurantAccountController.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
class RestaurantAccountControllerTest {
	
	@MockBean
	RestaurantAccountService service;
	
	@Autowired
	MockMvc mock;
	
	private UserDto makeUser() {
		UserDto user = new UserDto();
		user.setUserId("1");
		user.setUsername("TestUser");
		user.setActive(true);
		RoleDto role = new RoleDto();
		role.setRoleId(1);
		role.setName("test");
		user.setRole(role);
		return user;
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
			managers.add(makeUser());
		restaurant.setManager(managers);
		return restaurant;
		
	}
	
	@Test
	void addManagerTest() throws Exception {
		Mockito.when(service.addManager("1", makeUser())).thenReturn(makeRestaurant(true));
		
		mock.perform(put("/restaurant/1/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(makeUser())))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("1"))
		.andExpect(jsonPath("$.manager").isNotEmpty());
	}
	
	@Test
	void addManagerNotFoundTest() throws Exception {
		mock.perform(put("/restaurant/2/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(makeUser())))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	void addManagerByIdTest() throws Exception {
		Mockito.when(service.addManager("1", "1")).thenReturn(makeRestaurant(true));
		
		mock.perform(put("/restaurant/1/manager/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(makeUser())))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("1"))
		.andExpect(jsonPath("$.manager").isNotEmpty());
	}
	
	@Test
	void addManagerByIdNotFoundTest() throws Exception {
		mock.perform(put("/restaurant/2/manager/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	void deleteManagerTest() throws Exception {
		Mockito.when(service.removeManager("2", makeUser())).thenReturn(makeRestaurant(false));
		
		mock.perform(delete("/restaurant/2/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(makeUser())))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("2"))
		.andExpect(jsonPath("$.manager").isEmpty());
	}
	
	@Test
	void deleteManagerNotFoundTest() throws Exception {
		mock.perform(delete("/restaurant/2/manager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(makeUser())))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	void deleteManagerByIdTest() throws Exception {
		Mockito.when(service.removeManager("2", "1")).thenReturn(makeRestaurant(false));
		
		mock.perform(delete("/restaurant/2/manager/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.restaurantId").value("2"))
		.andExpect(jsonPath("$.manager").isEmpty());
	}
	
	@Test
	void deleteManagerByIdNotFoundTest() throws Exception {
		mock.perform(delete("/restaurant/2/manager/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
