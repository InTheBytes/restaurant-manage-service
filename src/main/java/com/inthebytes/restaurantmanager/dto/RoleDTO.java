package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.ManagerRoleModel;

@Repository
public interface RoleDTO extends JpaRepository<ManagerRoleModel, Long> {
	ManagerRoleModel findRoleByName(String name);
}