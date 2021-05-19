package com.inthebytes.restaurantmanager.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.inthebytes.restaurantmanager.dto.LocationDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDto;
import com.inthebytes.restaurantmanager.dto.UserDto;
import com.inthebytes.restaurantmanager.entity.Location;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.entity.Role;
import com.inthebytes.restaurantmanager.entity.User;
import com.inthebytes.restaurantmanager.mapper.RestaurantMapper;
import com.inthebytes.restaurantmanager.mapper.UserMapper;
import com.inthebytes.restaurantmanager.repository.RestaurantDao;
import com.inthebytes.restaurantmanager.repository.RoleDao;
import com.inthebytes.restaurantmanager.repository.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantAccountServiceTest {

	@Mock
	private RestaurantDao restaurantRepo;
	
	@Mock
	private RoleDao roleRepo;
	
	@Mock
	private UserDao userRepo;
	
	@Mock
	private RestaurantMapper restaurantMapper;
	
	@Mock
	private UserMapper userMapper;
	
	@InjectMocks
	RestaurantAccountService service;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(userMapper.convert(makeManager(true))).thenReturn(makeManagerDto());
	}
	
	@Test
	public void addManagerWithDtoTest() {
		
	}
	
	@Test
	public void addManagerDtoNoUserTest() {
		
	}
	
	@Test
	public void addManagerDtoNoRestaurantTest() {
		
	}
	
	@Test
	public void addManagerWithIdTest() {
		
	}
	
	@Test
	public void addManagerIdNoUserTest() {
		
	}
	
	@Test
	public void addManagerIdNoRestaurantTest() {
		
	}
	
	@Test
	public void removeManagerWithDtoTest() {
		
	}
	
	@Test
	public void removeManagerDtoNoUserTest() {
		
	}
	
	@Test
	public void removeManagerDtoNoRestaurantTest() {
		
	}
	
	@Test
	public void removeManagerWithIdTest() {
		
	}
	
	@Test
	public void removeManagerIdNoUserTest() {
		
	}
	
	@Test
	public void removeManagerIdNoRestaurantTest() {
		
	}
	
	private void happyPath() {
	}
	

	private Role getRole(Boolean isManager) {
		Role role = new Role();
		role.setRoleId(26L);
		if (isManager)
			role.setName("restaurant");
		else
			role.setName("user");
		return role;
	}
	
	private RestaurantDTO makeRestaurantDTO() {
		LocationDTO location = new LocationDTO("Main St.", "123", "Sacramento", "California", 11111);
		RestaurantDTO test = new RestaurantDTO("Lexi's Burgers", "Fast Food", location);

		return test;
	}
	
	private Restaurant makeRestaurantEntity(Boolean hasManager) {
		Restaurant test = new Restaurant();
		test.setName("Lexi's Burgers");

		Location location = new Location();
		location.setStreet("Main St.");
		location.setUnit("123");
		location.setCity("Sacramento");
		location.setState("California");
		location.setZipCode(95838);
		test.setLocation(location);

		test.setCuisine("Fast Food");

		if (hasManager) {
			
		}
		return test;
	}
	
	private UserDto makeManagerDto() {
		UserDto manager = new UserDto();
		manager.setUserId(1L);
		manager.setUsername("lexnel");
		RoleDto role = new RoleDto();
		role.setName("restaurant");
		role.setRoleId(26L);
		manager.setRole(role);
		manager.setIsActive(true);
		return manager;
	}
	
	private User makeManager(Boolean isActive) {
		User manager = new User();
		manager.setUserId(1L);
		manager.setPassword("password");
		manager.setUsername("lexnel");
		manager.setFirstName("Lexi");
		manager.setLastName("Nelson");
		manager.setPhone("00000000000");
		manager.setEmail("email@email.com");
		manager.setRole(getRole(isActive));
		manager.setActive(isActive);
		return manager;
	}
}
