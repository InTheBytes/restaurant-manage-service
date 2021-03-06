package com.inthebytes.restaurantmanager;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.inthebytes.stacklunch.security.StackLunchSecurityConfig;

@Configuration
@EnableWebSecurity
public class RestaurantManagerSecurity extends StackLunchSecurityConfig {

	@Override
	public HttpSecurity addSecurityConfigs(HttpSecurity security) throws Exception {
		return security.authorizeRequests()
				.antMatchers("/**").permitAll()
				.and();
	}

}
