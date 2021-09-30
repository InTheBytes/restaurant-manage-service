package com.inthebytes.restaurantmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.restaurantmanager.dao.RoleDao;
import com.inthebytes.restaurantmanager.dao.UserDao;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.role.RoleDto;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RestaurantAccountServiceTest {

	@Mock
	private RestaurantDao restaurantRepo;
	
	@Mock
	private RoleDao roleRepo;
	
	@Mock
	private UserDao userRepo;
	
	@InjectMocks
	RestaurantAccountService service;
	
	private Restaurant restaurantEntity;
	private User managerEntity;
	private RestaurantDto restaurantDto;
	private UserDto userDto;

	private Role getRole(Boolean isManager) {
		Role role = new Role();
		role.setRoleId((isManager) ? 1 : 2);
		role.setName((isManager) ? "restaurant" : "user");
		return role;
	}
	
	private void initializeUsers() {
		managerEntity = new User();
		managerEntity.setUserId("1");
		
		userDto = new UserDto();
		userDto.setUserId("1");
		userDto.setUsername("Test");
		userDto.setActive(false);
		RoleDto role = new RoleDto();
		role.setRoleId(2);
		role.setName("user");
		userDto.setRole(role);
	}
	
	private void initializeRestaurants() {
		restaurantEntity = new Restaurant();
		restaurantEntity.setRestaurantId("1");
		restaurantEntity.setName("Test Restaurant");
		restaurantEntity.setManager(new HashSet<User>());
		
		LocationDto location = new LocationDto();
		location.setUnit("");
		location.setStreet("");
		location.setCity("");
		location.setState("");
		location.setZipCode(11111);
		
		restaurantDto = new RestaurantDto();
		restaurantDto.setName("Test Restaurant");
		restaurantDto.setCuisine("Test");
		restaurantDto.setLocation(location);
		restaurantDto.setRestaurantId("1");
	}
	
	@BeforeAll
	public void setUp() {
		initializeUsers();
		initializeRestaurants();
	}
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void addManagerWithDtoTest() {
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		
		assertThat(service.addManager("1", userDto)).isEqualTo(restaurantDto);
	}
	
	@Test
	public void addManagerDtoNoUserTest() {
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(null);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		
		assertThat(service.addManager("1", userDto)).isNull();
	}
	
	@Test
	public void addManagerDtoNoRestaurantTest() {
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		when(UserDto.convert(managerEntity)).thenReturn(userDto);
		
		assertThat(service.addManager("1", userDto)).isNull();
	}
	
	@Test
	public void addManagerWithIdTest() {
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		when(UserDto.convert(managerEntity)).thenReturn(userDto);
		
		assertThat(service.addManager("1", "1")).isEqualTo(restaurantDto);
	}
	
	@Test
	public void addManagerIdNoUserTest() {
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(null);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		when(UserDto.convert(managerEntity)).thenReturn(userDto);
		
		assertThat(service.addManager("1", "1")).isNull();
	}
	
	@Test
	public void addManagerIdNoRestaurantTest() {
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		when(UserDto.convert(managerEntity)).thenReturn(userDto);
		
		assertThat(service.addManager("1", "1")).isNull();
	}
	
	@Test
	public void removeManagerWithDtoTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		
		assertThat(service.removeManager("1", userDto)).isEqualTo(restaurantDto);
	}
	
	@Test
	public void removeManagerDtoNoUserTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(null);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
	
	@Test
	public void removeManagerDtoNoRestaurantTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
	
	@Test
	public void removeManagerWithIdTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		when(UserDto.convert(managerEntity)).thenReturn(userDto);
		
		assertThat(service.removeManager("1", userDto)).isEqualTo(restaurantDto);
	}
	
	@Test
	public void removeManagerIdNoUserTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(null);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		when(userRepo.findByUserId("1")).thenReturn(null);
		when(UserDto.convert(managerEntity)).thenReturn(userDto);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
	
	@Test
	public void removeManagerIdNoRestaurantTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		when(RestaurantDto.convert(restaurantEntity)).thenReturn(restaurantDto);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		when(UserDto.convert(managerEntity)).thenReturn(userDto);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
}
