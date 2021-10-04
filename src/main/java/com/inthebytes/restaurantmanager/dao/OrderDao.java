package com.inthebytes.restaurantmanager.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderRepository;

@Repository
public interface OrderDao extends OrderRepository{

	Set<Order> findByFoodsFoodFoodId(String foodId);
	
	default Set<Order> getOrdersByFoodId(String foodId) {
		return findByFoodsFoodFoodId(foodId);
	}
}
