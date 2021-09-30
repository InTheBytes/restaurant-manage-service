package com.inthebytes.restaurantmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.inthebytes.stacklunch.StackLunchApplication;

@SpringBootApplication
@EnableJpaRepositories
public class RestaurantmanagerApplication {

	public static void main(String[] args) {
		StackLunchApplication.run(RestaurantmanagerApplication.class, args);
	}
}