package com.inthebytes.restaurantmanager.dao;

import com.inthebytes.restaurantmanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {
	Role findByName(String roleName);
}