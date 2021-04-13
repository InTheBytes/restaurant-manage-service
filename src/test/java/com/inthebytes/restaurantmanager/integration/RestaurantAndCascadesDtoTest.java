package com.inthebytes.restaurantmanager.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.inthebytes.restaurantmanager.dto.ManagerDTO;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.dto.RoleDTO;
import com.inthebytes.restaurantmanager.entity.Manager;
import com.inthebytes.restaurantmanager.entity.ManagerRole;
import com.inthebytes.restaurantmanager.entity.Restaurant;

@DataJpaTest
public class RestaurantAndCascadesDtoTest {
	
	@Autowired
	RestaurantDTO restaurantRepo;
	
	@Autowired
	ManagerDTO managerRepo;
	
	@Autowired
	RoleDTO roleRepo;
	
	@Test
	public void testManagerRepo() {
		ManagerRole role = new ManagerRole();
		role.setName("restaurant");
		roleRepo.save(role);
		
		Manager test = new Manager();
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
		Restaurant test = new Restaurant();
		test.setName("Lexi's Burgers");
		
		Manager manager = new Manager();
		manager.setFirstName("Restaurant");
		manager.setRole(roleRepo.findRoleByName("restaurant"));
		manager.setLastName("Manager");
		manager.setEmail("someone@email.com");
		manager.setPassword("HELLO!!!");
		manager.setPhone("111-111-1111");
		manager.setUsername("myManagaer");
		manager.setIsActive(false);
		test.setManager(manager);
		
		test = restaurantRepo.save(test);
		assertThat(restaurantRepo.findByName("Lexi's Burgers")).isNotNull();
		assertThat(managerRepo.findById(test.getManager().getManagerId())).isNotNull();
		restaurantRepo.delete(test);
		managerRepo.delete(test.getManager());
		assertThat(restaurantRepo.findByName("Lexi's Burgers")).isNull();
		assertThat(managerRepo.findById(test.getManager().getManagerId())).isEmpty();
	}
}
