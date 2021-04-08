package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.LocationModel;

@Repository
public interface LocationDTO extends JpaRepository<LocationModel, Long> {
	LocationModel findByLocationId(Long id);
}
