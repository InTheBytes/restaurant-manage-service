package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.HoursModel;

@Repository
public interface HoursDTO extends JpaRepository<HoursModel, Long> {
	HoursModel findByHoursId(Long id);
}
