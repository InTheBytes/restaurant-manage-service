package com.inthebytes.restaurantmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.mapper.RestaurantMapper;
import com.inthebytes.restaurantmanager.repository.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private RestaurantMapper mapper;

	public RestaurantDTO createRestaurant(RestaurantDTO restaurant) {
		if (restaurantRepo.findByName(restaurant.getName()) != null)
			throw new EntityExistsException();
		return mapper.convert(restaurantRepo.save(mapper.convert(restaurant)));
	}

	public Boolean deleteRestaurant(Long restaurantId) {
		Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId);
		if (restaurant == null)
			return false;
		restaurantRepo.delete(restaurant);
		return true;
	}

	public List<RestaurantDTO> getRestaurant() {
		return restaurantRepo.findAll().stream().map((x) -> mapper.convert(x)).collect(Collectors.toList());
	}
	
	public RestaurantDTO getRestaurant(Long restuarantId) {
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
