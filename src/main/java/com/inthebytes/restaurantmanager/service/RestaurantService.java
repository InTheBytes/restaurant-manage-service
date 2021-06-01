package com.inthebytes.restaurantmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
import com.inthebytes.restaurantmanager.entity.Restaurant;
import com.inthebytes.restaurantmanager.mapper.RestaurantMapper;
import com.inthebytes.restaurantmanager.repository.RestaurantDao;

@Service
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

	public List<RestaurantDTO> getRestaurant() {
		return restaurantRepo.findAll().stream().map((x) -> mapper.convert(x)).collect(Collectors.toList());
	}
	
	
	public List<List<RestaurantDTO>> getRestaurantPages(Integer pageSize) {
		List<RestaurantDTO> manuscript = getRestaurant();
		Map<Integer, List<RestaurantDTO>> pages = manuscript.stream().collect(Collectors.groupingBy(x -> manuscript.indexOf(x)/pageSize));
		List<List<RestaurantDTO>> paginated = new ArrayList<List<RestaurantDTO>>(pages.values());
		return paginated;
	}
	

	public RestaurantDTO getRestaurantByName(String name) {
		Restaurant restaurant = restaurantRepo.findByName(name);
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
