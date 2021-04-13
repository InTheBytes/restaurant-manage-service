package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.Manager;

@Repository
public interface ManagerDTO extends JpaRepository<Manager, Long>{
	Manager findByManagerId(Long id);
}
