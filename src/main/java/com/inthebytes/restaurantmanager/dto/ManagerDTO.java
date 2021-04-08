package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.ManagerModel;

@Repository
public interface ManagerDTO extends JpaRepository<ManagerModel, Long>{
	ManagerModel findByManagerId(Long id);
}
