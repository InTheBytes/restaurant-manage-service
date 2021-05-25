package com.inthebytes.restaurantmanager.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inthebytes.restaurantmanager.dto.RoleDto;
import com.inthebytes.restaurantmanager.dto.UserDto;
import com.inthebytes.restaurantmanager.entity.Role;
import com.inthebytes.restaurantmanager.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserMapperTest {

	@Autowired 
	UserMapper mapper;
	
	private User userEntity;
	private UserDto userDto;
	
	private void makeDto() {
		userDto = new UserDto();
		RoleDto role = new RoleDto();
		role.setRoleId(1L);
		role.setName("Test");
		userDto.setRole(role);
		userDto.setUserId(1L);
		userDto.setUsername("Test");
		userDto.setIsActive(true);
	}
	
	private void makeEntity() {
		userEntity = new User();
		Role role = new Role();
		role.setRoleId(1L);
		role.setName("Test");
		userEntity.setRole(role);
		userEntity.setUserId(1L);
		userEntity.setUsername("Test");
		userEntity.setActive(true);
	}
	
	@BeforeAll
	public void setUp() {
		makeDto();
		makeEntity();
	}
	
	@Test
	public void userConvertTest() {
		assertThat(mapper.convert(userEntity)).isEqualTo(userDto);
	}
	
	@Test
	public void nullUserConvertTest() {
		User user = null;
		assertThat(mapper.convert(user)).isNull();
	}
	
	@Test
	public void emptyUserConvertTest() {
		User user = new User();
		assertThat(mapper.convert(user)).isNull();
	}
	
	@Test
	public void roleConvertTest() {
		assertThat(mapper.convert(userEntity.getRole())).isEqualTo(userDto.getRole());
	}
	
	@Test
	public void nullRoleConvertTest() {
		Role role = null;
		assertThat(mapper.convert(role)).isNull();
	}
	
	@Test
	public void emptyRoleConvertTest() {
		Role role = new Role();
		assertThat(mapper.convert(role)).isNull();
	}
}
