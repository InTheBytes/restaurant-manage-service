package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.MenuModel;

@Repository
public interface MenuDTO extends JpaRepository<MenuModel, Long> {
	MenuModel findByMenuId(Long id);
	MenuModel findByTitle(String title);
}
