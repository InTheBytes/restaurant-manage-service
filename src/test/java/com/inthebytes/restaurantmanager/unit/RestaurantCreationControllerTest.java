package com.inthebytes.restaurantmanager.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.restaurantmanager.control.RestaurantCreationController;
import com.inthebytes.restaurantmanager.entity.GenreModel;
import com.inthebytes.restaurantmanager.entity.HoursModel;
import com.inthebytes.restaurantmanager.entity.LocationModel;
import com.inthebytes.restaurantmanager.entity.ManagerModel;
import com.inthebytes.restaurantmanager.entity.ManagerRoleModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;
import com.inthebytes.restaurantmanager.entity.RestaurantModel;
import com.inthebytes.restaurantmanager.service.RestaurantCreationService;

@WebMvcTest(RestaurantCreationController.class)
@AutoConfigureMockMvc
public class RestaurantCreationControllerTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private RestaurantCreationController control;

	@MockBean
	private RestaurantCreationService service;

	@Test
	public void addRestaurantTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(restaurant);

		mock.perform(post("/creator/restaurant/confirmation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string("Restaurant-ID", Matchers.containsString("1")));
	}

	@Test
	public void addRestaurantInvalidTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(null);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(true);

		mock.perform(post("/creator/restaurant/confirmation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
	}

	@Test
	public void addRestaurantNonexistentTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(null);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(false);

		mock.perform(post("/creator/restaurant/confirmation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void startRestaurantCreationTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.startRestaurant(restaurant)).thenReturn(restaurant);

		mock.perform(post("/creator/restaurant/starter")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().string("Restaurant-ID", Matchers.containsString("1")));
	}

	@Test
	public void startRestaurantCreationInvalidTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.startRestaurant(restaurant)).thenReturn(null);

		mock.perform(post("/creator/restaurant/starter")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
	}

	@Test
	public void updateRestaurantCreationTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.updateRestaurant(restaurant)).thenReturn(restaurant);

		mock.perform(put("/creator/restaurant/{restaurantId}", restaurant.getRestaurantId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string("Restaurant-ID", Matchers.containsString("1")));
	}

	@Test
	public void updateRestaurantCreationInvalidTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(null);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(true);

		mock.perform(put("/creator/restaurant/{restaurantId}", restaurant.getRestaurantId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Test
	public void updateRestaurantCreationNonexistentTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.submitRestaurant(restaurant)).thenReturn(null);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(false);

		mock.perform(put("/creator/restaurant/{restaurantId}", restaurant.getRestaurantId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(restaurant)))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void viewRestaurantCreationTest() throws Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.getRestaurantInProgress(1L)).thenReturn(restaurant);

		mock.perform(get("/creator/restaurant/preview/{restaurantId}", 1L)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void viewRestaurantCreationNonexistentTest() throws JsonProcessingException, Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.getRestaurantInProgress(restaurant.getRestaurantId())).thenReturn(null);

		mock.perform(get("/creator/restaurant/preview/{restaurantId}", 
				restaurant.getRestaurantId())).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void cancelRestaurantCreationTest() throws Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.trashRestaurantProgress(restaurant.getRestaurantId())).thenReturn(true);

		mock.perform(delete("/creator/restaurant/{restaurantId}", restaurant.getRestaurantId()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string("Deleted-Restaurant-ID", Matchers.containsString("1")));
	}

	@Test
	public void cancelRestaurantCreationInvalidTest() throws Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.trashRestaurantProgress(restaurant.getRestaurantId())).thenReturn(false);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(true);

		mock.perform(delete("/creator/restaurant/{restaurantId}", restaurant.getRestaurantId()))
		.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Test
	public void cancelRestaurantCreationNonexistentTest() throws Exception {
		RestaurantModel restaurant = makeRestaurantModel();
		when(service.trashRestaurantProgress(restaurant.getRestaurantId())).thenReturn(false);
		when(service.isSaved(restaurant.getRestaurantId())).thenReturn(false);

		mock.perform(delete("/creator/restaurant/{restaurantId}", restaurant.getRestaurantId()))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	private RestaurantModel makeRestaurantModel() {
		RestaurantModel test = new RestaurantModel();
		test.setRestaurantId(1L);
		test.setName("Lexi's Burgers");

		test.setHours(new HoursModel());
		test.setMenus(new ArrayList<MenuModel>());

		ManagerRoleModel role = new ManagerRoleModel();
		role.setName("restaurant");

		ManagerModel manager = new ManagerModel();
		manager.setFirstName("Restaurant");
		manager.setRole(role);
		manager.setLastName("Manager");
		manager.setEmail("someone@email.com");
		manager.setPassword("HELLO!!!");
		manager.setPhone("111-111-1111");
		manager.setUsername("myManagaer");
		manager.setIsActive(false);
		test.setManager(manager);

		List<LocationModel> locations = new ArrayList<LocationModel>();
		LocationModel location = new LocationModel();
		location.setStreet("Main St.");
		location.setStreetAddition("");
		location.setUnit("123");
		location.setCity("Sacramento");
		location.setState("California");
		location.setZip(95838);
		locations.add(location);
		test.setLocations(locations);

		List<GenreModel> genres = new ArrayList<GenreModel>();
		GenreModel genre = new GenreModel();
		genre.setTitle("Fair Food");
		genres.add(genre);
		test.setGenres(genres);

		return test;
	}
}
