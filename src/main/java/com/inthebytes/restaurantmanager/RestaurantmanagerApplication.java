package com.inthebytes.restaurantmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.inthebytes.stacklunch.StackLunchApplication;

@SpringBootApplication
public class RestaurantmanagerApplication {

	public static void main(String[] args) {
		StackLunchApplication.run(RestaurantmanagerApplication.class, args);
	}
}