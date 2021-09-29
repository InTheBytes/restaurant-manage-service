package com.inthebytes.restaurantmanager.service;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.restaurantmanager.dao.RestaurantDao;
import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.mapper.RestaurantMapper;

@Service
@Transactional
public class RestaurantService {

	@Autowired
	private RestaurantDao restaurantRepo;

	@Autowired
	private RestaurantMapper mapper;

	public RestaurantDTO createRestaurant(RestaurantDTO restaurant) {
		if (restaurantRepo.findByName(restaurant.getName()) != null)
			throw new EntityExistsException();
		return mapper.convert(restaurantRepo.save(mapper.convert(restaurant)));
	}

	public Boolean deleteRestaurant(String restaurantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		if (restaurant == null)
			return false;
		restaurantRepo.delete(restaurant);
		return true;
	}
	
	public Page<RestaurantDTO> getRestaurantPages(Integer page, Integer pageSize) {
		return restaurantRepo.findAll(PageRequest.of(page, pageSize)).map((x) -> mapper.convert(x));
	}
	

	public RestaurantDTO getRestaurantByName(String name) {
		Restaurant restaurant = restaurantRepo.findByName(name);
		if (restaurant == null)
			return null;
		else
			return mapper.convert(restaurant);
	}
	
	public RestaurantDTO getRestaurantByManagerID(String id) {
		Restaurant restaurant = restaurantRepo.findByManagerUserId(id);
		if (restaurant == null)
			return null;
		else
			return mapper.convert(restaurant);
	}
	
	public RestaurantDTO getRestaurant(String restuarantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restuarantId);
		if (restaurant == null)
			return null;
		else
			return mapper.convert(restaurant);
	}
	
	public RestaurantDTO updateRestaurant(RestaurantDTO restaurant) {
		Restaurant restaurantEntity = restaurantRepo.findByRestaurantId(restaurant.getRestaurantId());
		if (restaurantEntity == null)
			return null;
		else {
			restaurantEntity = mapper.convert(restaurant);
			return mapper.convert(restaurantRepo.save(restaurantEntity));
		}
	}
}
