package com.inthebytes.restaurantmanager;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.inthebytes.stacklunch.StackLunchDataSource;
import com.inthebytes.stacklunch.UniversalStackLunchConfiguration;

@Configuration
@Import({UniversalStackLunchConfiguration.class, RestaurantManagerSecurity.class, StackLunchDataSource.class})
public class RestaurantManagerTestConfig {

}
