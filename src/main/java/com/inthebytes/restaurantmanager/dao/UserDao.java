package com.inthebytes.restaurantmanager.dao;

import com.inthebytes.restaurantmanager.entity.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, String> {
	User findByEmailIgnoreCase(String email);
	List<User> findAll();
	User findByUsername(String username);
	User findByUserId(String userId);
}
