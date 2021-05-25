package com.inthebytes.restaurantmanager.mapper;

import org.springframework.stereotype.Component;

import com.inthebytes.restaurantmanager.dto.RoleDto;
import com.inthebytes.restaurantmanager.dto.UserDto;
import com.inthebytes.restaurantmanager.entity.Role;
import com.inthebytes.restaurantmanager.entity.User;

@Component
public class UserMapper {
	
	public UserDto convert(User user) {
		if (user == null ||
				user.getUserId() == null ||
				user.getUsername() == null ||
				user.getRole() == null ||
				user.getActive() == null)
			return null;
		UserDto result = new UserDto();
		result.setUserId(user.getUserId());
		result.setUsername(user.getUsername());
		result.setIsActive(user.getActive());
		result.setRole(convert(user.getRole()));
		return result;
	}
	
	public RoleDto convert(Role role) {
		if (role == null ||
				role.getRoleId() == null ||
				role.getName() == null)	
			return null;
		RoleDto result = new RoleDto();
		result.setRoleId(role.getRoleId());
		result.setName(role.getName());
		return result;
	}

}
