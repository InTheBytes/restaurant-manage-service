package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.ManagerRole;

@Repository
public interface RoleDTO extends JpaRepository<ManagerRole, Long> {
	ManagerRole findRoleByName(String name);
}