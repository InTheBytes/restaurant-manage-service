package com.inthebytes.restaurantmanager.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.inthebytes.restaurantmanager.service.RestaurantService;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc
public class RestaurantControllerTest {
	
	@Autowired
	private MockMvc mock;

	@MockBean
	private RestaurantService service;
	
	@Test
	public void deleteRestaurantTest() throws Exception{
		when(service.deleteRestaurant(22L)).thenReturn(true);
		
		mock.perform(delete("/apis/restaurant/22"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string("Deleted-Restaurant-ID", Matchers.containsString("22")));
	}

	@Test
	public void deleteNonexistentRestaurantTest() throws Exception {
		when(service.deleteRestaurant(22L)).thenReturn(false);
		
		mock.perform(delete("/apis/restaurant/22"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
