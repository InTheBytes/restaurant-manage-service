package com.inthebytes.restaurantmanager.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.inthebytes.restaurantmanager.dto.CustomizationDTO;
import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.GenreDTO;
import com.inthebytes.restaurantmanager.dto.HoursDTO;
import com.inthebytes.restaurantmanager.dto.ManagerDTO;
import com.inthebytes.restaurantmanager.dto.MenuDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDTO;
import com.inthebytes.restaurantmanager.entity.GenreModel;
import com.inthebytes.restaurantmanager.entity.HoursModel;
import com.inthebytes.restaurantmanager.entity.LocationModel;
import com.inthebytes.restaurantmanager.entity.ManagerModel;
import com.inthebytes.restaurantmanager.entity.ManagerRoleModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;
import com.inthebytes.restaurantmanager.entity.RestaurantModel;
import com.inthebytes.restaurantmanager.service.RestaurantCreationService;
import com.inthebytes.restaurantmanager.service.RestaurantVerificationService;

@WebMvcTest(RestaurantCreationService.class)
public class RestaurantCreationServiceTest {

	@InjectMocks
	RestaurantCreationService service;
	
	@MockBean
	RestaurantVerificationService verification;
	@MockBean
	BCryptPasswordEncoder encoder;
	
	@MockBean
	CustomizationDTO customizationRepo;
	@MockBean
	FoodDTO foodRepo;
	@MockBean
	HoursDTO hoursRepo;
	@MockBean
	ManagerDTO managerRepo;
	@MockBean
	RoleDTO roleRepo;
	@MockBean
	MenuDTO menuRepo;
	@MockBean
	GenreDTO genreRepo;
	@MockBean
	RestaurantDTO restaurantRepo;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getRestaurantInProgressTest() {
		RestaurantModel test = makeRestaurantModel();
		when(restaurantRepo.findByRestaurantId(test.getRestaurantId())).thenReturn(test);
		RestaurantModel test2 = makeRestaurantModel();
		
		test2.setRestaurantId(2L);
		test2.getManager().setIsActive(true);
		when(restaurantRepo.findByRestaurantId(2L)).thenReturn(test2);
		when(restaurantRepo.findByRestaurantId(3L)).thenReturn(null);
		
		assertThat(service.getRestaurantInProgress(test.getRestaurantId())).isNotNull();
		assertThat(service.getRestaurantInProgress(test2.getRestaurantId())).isNull();
		assertThat(service.getRestaurantInProgress(3L)).isNull();
	}
	
	@Test
	public void isSavedTest() {
		RestaurantModel test = makeRestaurantModel();
		when(restaurantRepo.findByRestaurantId(test.getRestaurantId())).thenReturn(test);
		when(restaurantRepo.findByRestaurantId(2L)).thenReturn(null);
		
		assertTrue(service.isSaved(test.getRestaurantId()));
		assertFalse(service.isSaved(2L));
	}
	
	@Test
	public void trashRestaurantProgress() {
		RestaurantModel test = makeRestaurantModel();
		when(restaurantRepo.findByRestaurantId(test.getRestaurantId())).thenReturn(test);
		when(restaurantRepo.findByRestaurantId(2L)).thenReturn(null);
		
		assertTrue(service.trashRestaurantProgress(test.getRestaurantId()));
		assertFalse(service.trashRestaurantProgress(2L));
	}

	@Test
	public void submitRestaurantTest() {
		RestaurantModel test = makeRestaurantModel();
		when(restaurantRepo.findByRestaurantId(test.getRestaurantId())).thenReturn(test);
		when(restaurantRepo.findByName(test.getName())).thenReturn(test);
		when(restaurantRepo.save(test)).thenReturn(test);
		when(managerRepo.save(test.getManager())).thenReturn(test.getManager());
		when(verification.checkForFinished(test)).thenReturn(true);
		
		assertThat(service.submitRestaurant(test)).isNotNull();
		assertThat(service.submitRestaurant(new RestaurantModel())).isNull();
	}
	
	@Test
	public void startRestaurantTest() {
		RestaurantModel test = makeRestaurantModel();
		when(restaurantRepo.save(test)).thenReturn(test);
		when(hoursRepo.save(test.getHours())).thenReturn(test.getHours());
		when(verification.checkManager(test.getManager())).thenReturn(true);
		when(verification.checkBasics(test)).thenReturn(true);
		when(verification.checkLocations(test)).thenReturn(true);
		when(verification.checkHours(test)).thenReturn(true);
		when(verification.checkGenres(test)).thenReturn(true);
		
		assertThat(service.startRestaurant(test)).isNotNull();
		assertThat(service.startRestaurant(new RestaurantModel())).isNull();
	}
	
	@Test
	public void updateRestaurantTest() {
		RestaurantModel test = makeRestaurantModel();
		when(restaurantRepo.findByRestaurantId(test.getRestaurantId())).thenReturn(test);
		when(hoursRepo.save(test.getHours())).thenReturn(test.getHours());
		
		assertThat(service.updateRestaurant(test)).isNotNull();
		assertThat(service.updateRestaurant(new RestaurantModel())).isNull();
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
