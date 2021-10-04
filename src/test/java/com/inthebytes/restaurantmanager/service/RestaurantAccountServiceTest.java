package com.inthebytes.restaurantmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class RestaurantAccountServiceTest {

	@Mock
	private RestaurantDao restaurantRepo;
	
	@Mock
	private RoleDao roleRepo;
	
	@Mock
	private UserDao userRepo;
	
	@Autowired
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
		restaurantDto.setManager(new HashSet<UserDto>());
		restaurantEntity = restaurantDto.convert();
	}
	
	@BeforeAll
	void setUp() {
		initializeUsers();
		initializeRestaurants();
	}
	
	@Test
	void addManagerWithDtoTest() {
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		
		RestaurantDto testDto = restaurantDto;
		UserDto testManager = new UserDto();
		testManager.setRole(RoleDto.convert(getRole(true)));
		testManager.setActive(true);
		testManager.setUserId("1");
		testDto.getManager().add(testManager);
		
		assertThat(service.addManager("1", userDto)).isEqualTo(testDto);
	}
	
	@Test
	void addManagerDtoNoUserTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(null);
		
		assertThat(service.addManager("1", userDto)).isNull();
	}
	
	@Test
	void addManagerDtoNoRestaurantTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		
		assertThat(service.addManager("1", userDto)).isNull();
	}
	
	@Test
	void addManagerWithIdTest() {
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername(managerEntity.getUsername())).thenReturn(managerEntity);
		when(roleRepo.findByName("restaurant")).thenReturn(getRole(true));
		when(userRepo.save(managerEntity)).thenReturn(managerEntity);
		when(restaurantRepo.save(restaurantEntity)).thenReturn(restaurantEntity);
		
		assertThat(service.addManager("1", "1")).isEqualTo(restaurantDto);
	}
	
	@Test
	void addManagerIdNoUserTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		
		assertThat(service.addManager("1", "1")).isNull();
	}
	
	@Test
	void addManagerIdNoRestaurantTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUserId("1")).thenReturn(managerEntity);
		
		assertThat(service.addManager("1", "1")).isNull();
	}
	
	@Test
	void removeManagerDtoNoUserTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(null);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
	
	@Test
	void removeManagerDtoNoRestaurantTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
	
	@Test
	void removeManagerIdNoUserTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(restaurantEntity);
		when(userRepo.findByUsername("Test")).thenReturn(null);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
	
	@Test
	void removeManagerIdNoRestaurantTest() {
		when(restaurantRepo.findByRestaurantId("1")).thenReturn(null);
		when(userRepo.findByUsername("Test")).thenReturn(managerEntity);
		
		assertThat(service.removeManager("1", userDto)).isNull();
	}
}
