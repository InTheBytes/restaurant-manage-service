package com.inthebytes.restaurantmanager.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.inthebytes.restaurantmanager.entity.Genre;
import com.inthebytes.restaurantmanager.entity.Manager;
import com.inthebytes.restaurantmanager.entity.ManagerRole;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.service.RestaurantVerificationService;

@WebMvcTest(RestaurantVerificationService.class)
public class RestaurantVerificationServiceTest {

	@InjectMocks
	RestaurantVerificationService service;
	
	@Test
	public void checkForFinishedTest() {
		Restaurant unfinished = new Restaurant();
		assertFalse(service.checkForFinished(unfinished));
		
		Restaurant finished = makeRestaurantModel();
		assertTrue(service.checkForFinished(finished));
	}
	
	@Test
	public void checkBasicsTest() {
		Restaurant unfinished = new Restaurant();
		assertFalse(service.checkBasics(unfinished));
		
		Restaurant finished = makeRestaurantModel();
		assertTrue(service.checkBasics(finished));
	}
	
	@Test
	public void checkManagerTest() {
		Restaurant unfinished = new Restaurant();
		assertFalse(service.checkManager(unfinished.getManager()));
		
		Restaurant finished = makeRestaurantModel();
		assertTrue(service.checkManager(finished.getManager()));
	}
	
	@Test
	public void checkListContentsTest() {
		Restaurant unfinished = new Restaurant();
		unfinished.setGenres(new ArrayList<Genre>());
		assertFalse(service.checkListContents(unfinished));
		
		Restaurant finished = makeRestaurantModel();
		assertTrue(service.checkListContents(finished));
	}

	@Test
	public void checkGenres() {
		Restaurant unfinished = new Restaurant();
		unfinished.setGenres(new ArrayList<Genre> ());
		assertFalse(service.checkGenres(unfinished));
		
		Restaurant finished = makeRestaurantModel();
		assertTrue(service.checkGenres(finished));
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

		List<Genre> genres = new ArrayList<Genre>();
		Genre genre = new Genre();
		genre.setTitle("Fair Food");
		genres.add(genre);
		test.setGenres(genres);

		return test;
	}
}
