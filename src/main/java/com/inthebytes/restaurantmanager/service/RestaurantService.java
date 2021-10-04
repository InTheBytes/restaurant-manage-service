package com.inthebytes.restaurantmanager.service;

import java.util.Set;
import java.util.Comparator;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.restaurantmanager.dao.OrderDao;
import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

@Service
@Transactional
public class RestaurantService {

	@Autowired
	private RestaurantDao restaurantRepo;
	
	@Autowired
	private OrderDao orderRepo;
	

	public RestaurantDto createRestaurant(RestaurantDto restaurant) {
		if (restaurantRepo.findByName(restaurant.getName()) != null)
			throw new EntityExistsException();
		return RestaurantDto.convert(restaurantRepo.save(restaurant.convert()));
	}

	public Boolean deleteRestaurant(String restaurantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		if (restaurant == null)
			return false;
		restaurantRepo.delete(restaurant);
		return true;
	}
	
	public Page<RestaurantDto> getRestaurantPages(Integer page, Integer pageSize) {
		return restaurantRepo.findAll(PageRequest.of(page, pageSize)).map((x) -> RestaurantDto.convert(x));
	}
	
	private RestaurantDto sortRestaurantDto(Restaurant restaurant) {
		if (restaurant == null) return null;
		RestaurantDto dto = RestaurantDto.convert(restaurant);
		if (dto.getFoods() == null) return dto;
		
		dto.getFoods().sort(new Comparator<FoodDto>() {
			public int compare(FoodDto food1, FoodDto food2) {
				return food1.getName().compareTo(food2.getName());
			}
		});
		
		return dto;
	}
	

	public RestaurantDto getRestaurantByName(String name) {
		Restaurant restaurant = restaurantRepo.findByName(name);
		return sortRestaurantDto(restaurant);
	}
	
	public RestaurantDto getRestaurantByManagerID(String id) {
		Restaurant restaurant = restaurantRepo.findByManagerUserId(id);
		return sortRestaurantDto(restaurant);
	}
	
	public RestaurantDto getRestaurant(String restuarantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restuarantId);
		return sortRestaurantDto(restaurant);
	}
	
	public RestaurantDto updateRestaurant(RestaurantDto restaurant) {
		Restaurant previousRestaurant = restaurantRepo.findByRestaurantId(restaurant.getRestaurantId());
		if (previousRestaurant == null)
			return null;
	
		final Restaurant entity = restaurant.convert();
//		handleDeletedFoods(previousRestaurant, entity.getFoods());
		entity.getFoods().stream().forEach(food -> food.setRestaurant(entity));
		return sortRestaurantDto(restaurantRepo.save(entity));
	}
	
	private void handleDeletedFoods(Restaurant previous, Set<Food> updatedFoodList) {
		previous.getFoods()
			.stream()
			.filter(food -> !updatedFoodList.contains(food))
			.forEach(food -> {
				Set<Order> orders = orderRepo.getOrdersByFoodId(food.getFoodId());
				orders.stream().forEach(order -> {
					order.getFoods()
						.stream()
						.filter(foodListing -> !foodListing.getFood().equals(food));
					orderRepo.save(order);
				});
			});
	}
}
