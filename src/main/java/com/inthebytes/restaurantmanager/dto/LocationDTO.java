package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.Location;

@Repository
public interface LocationDTO extends JpaRepository<Location, Long>{
	
}
