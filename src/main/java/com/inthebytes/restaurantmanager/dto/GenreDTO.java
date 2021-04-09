package com.inthebytes.restaurantmanager.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.restaurantmanager.entity.Genre;

@Repository
public interface GenreDTO extends JpaRepository<Genre, Long> {
	Genre findByGenreId(Long id);
	Genre findByGenreTitle(String title);
}
