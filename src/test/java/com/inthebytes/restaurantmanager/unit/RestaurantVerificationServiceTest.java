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

import com.inthebytes.restaurantmanager.entity.FoodModel;
import com.inthebytes.restaurantmanager.entity.GenreModel;
import com.inthebytes.restaurantmanager.entity.HoursModel;
import com.inthebytes.restaurantmanager.entity.LocationModel;
import com.inthebytes.restaurantmanager.entity.ManagerModel;
import com.inthebytes.restaurantmanager.entity.ManagerRoleModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;
import com.inthebytes.restaurantmanager.entity.RestaurantModel;
import com.inthebytes.restaurantmanager.service.RestaurantVerificationService;

@WebMvcTest(RestaurantVerificationService.class)
public class RestaurantVerificationServiceTest {

	@InjectMocks
	RestaurantVerificationService service;
	
	@Test
	public void checkForFinishedTest() {
		RestaurantModel unfinished = new RestaurantModel();
		assertFalse(service.checkForFinished(unfinished));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkForFinished(finished));
	}
	
	@Test
	public void checkBasicsTest() {
		RestaurantModel unfinished = new RestaurantModel();
		assertFalse(service.checkBasics(unfinished));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkBasics(finished));
	}
	
	@Test
	public void checkManagerTest() {
		RestaurantModel unfinished = new RestaurantModel();
		assertFalse(service.checkManager(unfinished.getManager()));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkManager(finished.getManager()));
	}
	
	@Test
	public void checkListContentsTest() {
		RestaurantModel unfinished = new RestaurantModel();
		unfinished.setGenres(new ArrayList<GenreModel>());
		unfinished.setLocations(new ArrayList<LocationModel>());
		unfinished.setMenus(new ArrayList<MenuModel>());
		unfinished.setHours(new HoursModel());
		assertFalse(service.checkListContents(unfinished));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkListContents(finished));
	}
	
	@Test
	public void checkFood() {
		FoodModel food = new FoodModel();
		assertFalse(service.checkFood(food));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkFood(finished.getMenus().get(0).getMenuItems().get(0)));
	}
	
	@Test
	public void checkMenuTest() {
		MenuModel menu = new MenuModel();
		menu.setMenuItems(new ArrayList<FoodModel>());
		assertFalse(service.checkMenu(menu));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkMenu(finished.getMenus().get(0)));
	}

	@Test
	public void checkHoursTest() {
		RestaurantModel unfinished = new RestaurantModel();
		unfinished.setHours(new HoursModel());
		assertFalse(service.checkHours(unfinished));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkHours(finished));
	}

	@Test
	public void checkGenres() {
		RestaurantModel unfinished = new RestaurantModel();
		unfinished.setGenres(new ArrayList<GenreModel> ());
		assertFalse(service.checkGenres(unfinished));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkGenres(finished));
	}
	
	@Test
	public void checkLocationTest() {
		LocationModel location = new LocationModel();
		assertFalse(service.checkLocation(location));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkLocation(finished.getLocations().get(0)));
	}

	@Test
	public void checkLocations() {
		RestaurantModel unfinished = new RestaurantModel();
		unfinished.setLocations(new ArrayList<LocationModel> ());
		assertFalse(service.checkLocations(unfinished));
		
		RestaurantModel finished = makeRestaurantModel();
		assertTrue(service.checkLocations(finished));
	}
	
	private RestaurantModel makeRestaurantModel() {
		RestaurantModel test = new RestaurantModel();
		test.setRestaurantId(1L);
		test.setName("Lexi's Burgers");
		
		HoursModel hours = new HoursModel();
		LocalTime open = LocalTime.parse("09:00");
		LocalTime close = LocalTime.parse("21:00");
		hours.setMondayOpen(open);
		hours.setTuesdayOpen(open);
		hours.setWednesdayOpen(open);
		hours.setThursdayOpen(open);
		hours.setFridayOpen(open);
		hours.setSaturdayOpen(open);
		hours.setSundayOpen(open);
		hours.setMondayClose(close);
		hours.setTuesdayClose(close);
		hours.setWednesdayClose(close);
		hours.setThursdayClose(close);
		hours.setFridayClose(close);
		hours.setSaturdayClose(close);
		hours.setSundayClose(close);
		test.setHours(hours);
		
		MenuModel menu = new MenuModel();
		menu.setTitle("Classic Cuisines");
		
		FoodModel food1 = new FoodModel();
		food1.setName("Hamburger");
		food1.setDescription("2 patty + bun");
		food1.setPrice(4.99);
		
		FoodModel food2 = new FoodModel();
		food2.setName("Pizza");
		food2.setDescription("Sauce and stuff on a dough disk");
		food2.setPrice(4.99);
		
		List<FoodModel> foods = new ArrayList<FoodModel> ();
		foods.add(food1);
		foods.add(food2);
		menu.setMenuItems(foods);
		List<MenuModel> menus = new ArrayList<MenuModel> ();
		menus.add(menu);
		test.setMenus(menus);
		
		
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
