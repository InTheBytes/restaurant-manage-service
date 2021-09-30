package com.inthebytes.restaurantmanager.dao;

import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.role.RoleRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends RoleRepository {
	Role findByName(String roleName);
}