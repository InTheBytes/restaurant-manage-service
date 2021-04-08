package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.CustomizationModel;

@Repository
public interface CustomizationDTO extends JpaRepository<CustomizationModel, Long> {
	CustomizationModel findByCustomizeId(Long id);
	CustomizationModel findByDescription(String description);
}
