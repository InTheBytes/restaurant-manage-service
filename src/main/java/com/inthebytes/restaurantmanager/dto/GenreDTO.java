package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.GenreModel;

@Repository
public interface GenreDTO extends JpaRepository<GenreModel, Long> {
	GenreModel findByGenreId(Long id);
	GenreModel findByGenreTitle(String title);
}
