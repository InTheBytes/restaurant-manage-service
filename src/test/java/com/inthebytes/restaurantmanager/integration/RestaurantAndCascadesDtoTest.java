package com.inthebytes.restaurantmanager.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.inthebytes.restaurantmanager.dto.GenreDTO;
import com.inthebytes.restaurantmanager.dto.HoursDTO;
import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.ManagerDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDTO;
import com.inthebytes.restaurantmanager.entity.GenreModel;
import com.inthebytes.restaurantmanager.entity.HoursModel;
import com.inthebytes.restaurantmanager.entity.LocationModel;
import com.inthebytes.restaurantmanager.entity.ManagerModel;
import com.inthebytes.restaurantmanager.entity.ManagerRoleModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;
import com.inthebytes.restaurantmanager.entity.RestaurantModel;

@DataJpaTest
public class RestaurantAndCascadesDtoTest {

	@Autowired
	HoursDTO hoursRepo;
	
	@Autowired
	LocationDTO locationRepo;
	
	@Autowired
	RestaurantDTO restaurantRepo;
	
	@Autowired
	ManagerDTO managerRepo;
	
	@Autowired
	GenreDTO genreRepo;
	
	@Autowired
	RoleDTO roleRepo;
	
	@Test
	public void testLocationRepo() {
		LocationModel test = new LocationModel();
		test.setStreet("Main St.");
		test.setStreetAddition("");
		test.setUnit("123");
		test.setCity("Sacramento");
		test.setState("California");
		test.setZip(95838);
		
		test = locationRepo.save(test);
		assertThat(locationRepo.findAll().size()).isNotZero();
		assertThat(locationRepo.findByLocationId(test.getLocationId()).getState()).isEqualTo("California");
		
		locationRepo.delete(test);
		assertThat(locationRepo.findAll().size()).isZero();
	}
	
	@Test
	public void testHoursRepo() {
		HoursModel test = new HoursModel();
		LocalTime open = LocalTime.parse("09:00");
		LocalTime close = LocalTime.parse("21:00");
		test.setMondayOpen(open);
		test.setTuesdayOpen(open);
		test.setWednesdayOpen(open);
		test.setThursdayOpen(open);
		test.setFridayOpen(open);
		test.setSaturdayOpen(open);
		test.setSundayOpen(open);
		test.setMondayClose(close);
		test.setTuesdayClose(close);
		test.setWednesdayClose(close);
		test.setThursdayClose(close);
		test.setFridayClose(close);
		test.setSaturdayClose(close);
		test.setSundayClose(close);
		
		hoursRepo.save(test);
		assertThat(hoursRepo.findAll().size()).isNotZero();
		hoursRepo.delete(test);
		assertThat(hoursRepo.findAll().size()).isZero();
	}
	
	public void testGenreRepo() {
		GenreModel test = new GenreModel();
		test.setTitle("Bistro");
		genreRepo.save(test);
		assertThat(genreRepo.findByGenreTitle("Bistro")).isNotNull();
		genreRepo.delete(test);
		assertThat(genreRepo.findAll().size()).isZero();
	}
	
	public void testManagerRepo() {
		ManagerRoleModel role = new ManagerRoleModel();
		role.setName("restaurant");
		roleRepo.save(role);
		
		ManagerModel test = new ManagerModel();
		test.setFirstName("Restaurant");
		test.setRole(roleRepo.findRoleByName("restaurant"));
		test.setLastName("Manager");
		test.setEmail("someone@email.com");
		test.setPassword("HELLO!!!");
		test.setPhone("111-111-1111");
		test.setUsername("myManagaer");
		
		test = managerRepo.save(test);
		assertThat(managerRepo.findAll().size()).isNotZero();
		assertThat(managerRepo.findByManagerId(test.getManagerId()).getFirstName()).isEqualTo("Restaurant");
		managerRepo.delete(test);
		assertThat(managerRepo.findAll().size()).isZero();
	}
	
	@Test
	public void testRestuarantDto() {
		RestaurantModel test = new RestaurantModel();
		test.setName("Lexi's Burgers");
		hoursRepo.save(new HoursModel());
		
		test.setHours(hoursRepo.findAll().get(0));
		test.setGenres(new ArrayList<GenreModel>());
		test.setLocations(new ArrayList<LocationModel>());
		test.setMenus(new ArrayList<MenuModel>());
		
		ManagerRoleModel role = new ManagerRoleModel();
//		role.setName("restaurant");
//		roleRepo.save(role);
		
		ManagerModel manager = new ManagerModel();
		manager.setFirstName("Restaurant");
		manager.setRole(roleRepo.findRoleByName("restaurant"));
		manager.setLastName("Manager");
		manager.setEmail("someone@email.com");
		manager.setPassword("HELLO!!!");
		manager.setPhone("111-111-1111");
		manager.setUsername("myManagaer");
		manager.setIsActive(false);
		
		System.out.println("\n\n\n\n\n\nTEST PRINT");
		System.out.println(manager.getRole().getName() + manager.getRole().getRoleId());
		test.setManager(manager);
		
		restaurantRepo.save(test);
		assertThat(restaurantRepo.findByName("Lexi's Burgers")).isNotNull();
		restaurantRepo.delete(test);
		assertThat(hoursRepo.findAll().size()).isNotZero();
		hoursRepo.delete(hoursRepo.findAll().get(0));
		assertThat(restaurantRepo.findAll().size()).isZero();
		assertThat(hoursRepo.findAll().size()).isZero();
		assertThat(managerRepo.findAll().size()).isZero();
	}
}
