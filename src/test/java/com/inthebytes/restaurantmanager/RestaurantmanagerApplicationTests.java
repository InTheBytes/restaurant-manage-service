package com.inthebytes.restaurantmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.inthebytes.restaurantmanager.control.RestaurantCreationController;

@SpringBootTest
class RestaurantCreationTests {
	
	@Autowired
	private RestaurantCreationController controller;
	
	@Autowired
	private MockMvc mock;
	
	@Test
	void confirmRestaurantTest() {
	}
	
	@Test
	void confirmInvalidRestaurantTest() {
	}
	
	@Test
	void updateUnsavedRestaurantTest() {
	}
	
	@Test
	void updateInvalidRestaurantTest() {
	}
	
	@Test
	void getUnsavedRestaurantTest() {
	}
	
	@Test
	void getSavedRestaurantTest() {
	}
	
	@Test
	void getNonExistentRestaurantTest() {
	}
}
