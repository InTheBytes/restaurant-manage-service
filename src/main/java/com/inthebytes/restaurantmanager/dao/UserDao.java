package com.inthebytes.restaurantmanager.dao;

import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserRepository;

import java.util.List;

@Repository
public interface UserDao extends UserRepository{
	User findByEmailIgnoreCase(String email);
	List<User> findAll();
	User findByUsername(String username);
	User findByUserId(String userId);
}
