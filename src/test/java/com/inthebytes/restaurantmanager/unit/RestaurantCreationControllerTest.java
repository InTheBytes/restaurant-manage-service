package com.inthebytes.restaurantmanager.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.inthebytes.restaurantmanager.control.RestaurantCreationController;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantCreationController.class)
@AutoConfigureMockMvc
public class RestaurantCreationControllerTest {

	@Autowired
	private MockMvc mock;
	
	@Autowired
	private RestaurantCreationController control;
}
