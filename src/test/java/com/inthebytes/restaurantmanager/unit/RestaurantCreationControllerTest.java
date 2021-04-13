package com.inthebytes.restaurantmanager.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.restaurantmanager.control.RestaurantCreationController;
import com.inthebytes.restaurantmanager.entity.Manager;
import com.inthebytes.restaurantmanager.entity.ManagerRole;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.service.RestaurantCreationService;

@WebMvcTest(RestaurantCreationController.class)
@AutoConfigureMockMvc
public class RestaurantCreationControllerTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private RestaurantCreationService service;

	@Test
	public void addRestaurantTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(restaurant);

		mock.perform(post("/apis/restaurant/confirmation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string("Restaurant-ID", Matchers.containsString("1")));
	}

	@Test
	public void addRestaurantInvalidTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(null);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(true);

		mock.perform(post("/apis/restaurant/confirmation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
	}

	@Test
	public void addRestaurantNonexistentTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(null);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(false);

		mock.perform(post("/apis/restaurant/confirmation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void startRestaurantCreationTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.startRestaurant(restaurant)).thenReturn(restaurant);

		mock.perform(post("/apis/restaurant/starter")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().string("Restaurant-ID", Matchers.containsString("1")));
	}

	@Test
	public void startRestaurantCreationInvalidTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.startRestaurant(restaurant)).thenReturn(null);

		mock.perform(post("/apis/restaurant/starter")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
	}

	@Test
	public void viewRestaurantCreationTest() throws Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.getRestaurantInProgress(1L)).thenReturn(restaurant);

		mock.perform(get("/apis/restaurant/preview/{restaurantId}", 1L)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void viewRestaurantCreationNonexistentTest() throws JsonProcessingException, Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.getRestaurantInProgress(restaurant.getRestaurantId())).thenReturn(null);

		mock.perform(get("/apis/restaurant/preview/{restaurantId}", 
				restaurant.getRestaurantId())).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void cancelRestaurantCreationTest() throws Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.trashRestaurantProgress(restaurant.getRestaurantId())).thenReturn(true);

		mock.perform(delete("/apis/restaurant/{restaurantId}", restaurant.getRestaurantId()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string("Deleted-Restaurant-ID", Matchers.containsString("1")));
	}

	@Test
	public void cancelRestaurantCreationInvalidTest() throws Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.trashRestaurantProgress(restaurant.getRestaurantId())).thenReturn(false);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(true);

		mock.perform(delete("/apis/restaurant/{restaurantId}", restaurant.getRestaurantId()))
		.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Test
	public void cancelRestaurantCreationNonexistentTest() throws Exception {
		Restaurant restaurant = makeRestaurantModel();
		when(service.trashRestaurantProgress(restaurant.getRestaurantId())).thenReturn(false);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(false);

		mock.perform(delete("/apis/restaurant/{restaurantId}", restaurant.getRestaurantId()))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	private Restaurant makeRestaurantModel() {
		Restaurant test = new Restaurant();
		test.setRestaurantId(1L);
		test.setName("Lexi's Burgers");
		
		ManagerRole role = new ManagerRole();
		role.setName("restaurant");

		Manager manager = new Manager();
		manager.setFirstName("Restaurant");
		manager.setRole(role);
		manager.setLastName("Manager");
		manager.setEmail("someone@email.com");
		manager.setPassword("HELLO!!!");
		manager.setPhone("111-111-1111");
		manager.setUsername("myManagaer");
		manager.setIsActive(false);
		test.setManager(manager);

		return test;
	}
}
