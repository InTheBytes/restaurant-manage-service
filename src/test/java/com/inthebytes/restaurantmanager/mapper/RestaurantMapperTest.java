//package com.inthebytes.restaurantmanager.mapper;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.inthebytes.restaurantmanager.dto.FoodDTO;
//import com.inthebytes.restaurantmanager.dto.LocationDTO;
//import com.inthebytes.restaurantmanager.dto.RestaurantDTO;
//import com.inthebytes.restaurantmanager.entity.Food;
//import com.inthebytes.restaurantmanager.entity.Location;
//import com.inthebytes.restaurantmanager.entity.Restaurant;
//
//@ExtendWith(MockitoExtension.class)
//public class RestaurantMapperTest {
//	
//	private RestaurantMapper mapper = new RestaurantMapper();
//	
////	@Before 
////    void init(){
////        mapper = new RestaurantMapper();
////    }
//
//	@Test
//	public void restaurantEntityToDtoTest() {
//		Restaurant tester = makeRestaurantEntity();
//		RestaurantDTO result = makeRestaurantDTO();
//		
//		System.out.println("\n\n\n\n\n\n\n"+mapper.toString());
//		assertEquals(mapper.convert(tester), result);
//	}
//	
//	@Test
//	public void restaurantDtoToEntityTest() {
//		RestaurantDTO tester = makeRestaurantDTO();
//		Restaurant result = makeRestaurantEntity();
//		
//		assertEquals(mapper.convert(tester), result);
//	}
//	
//	@Test
//	public void locationEntityToDtoTest() {
//		Location tester = makeRestaurantEntity().getLocation();
//		LocationDTO result = makeRestaurantDTO().getLocation();
//		
//		assertEquals(mapper.convert(tester), result);
//	}
//	
//	@Test
//	public void locationDtoToEntityTest() {
//		LocationDTO tester = makeRestaurantDTO().getLocation();
//		Location result = makeRestaurantEntity().getLocation();
//		
//		assertEquals(mapper.convert(tester), result);
//	}
//	
//	@Test
//	public void foodEntityToDtoTest() {
//		List<Food> testers = makeRestaurantEntity().getFoods();
//		List<FoodDTO> results = makeRestaurantDTO().getFoods();
//		
//		for (int i = 0; i < testers.size(); i++) {
//			assertEquals(mapper.convert(testers.get(i)), results.get(i));
//		}
//	}
//	
//	@Test
//	public void foodDtoToEntityTest() {
//		List<FoodDTO> testers = makeRestaurantDTO().getFoods();
//		List<Food> results = makeRestaurantEntity().getFoods();
//		
//		for (int i = 0; i < testers.size(); i++) {
//			assertEquals(mapper.convert(testers.get(i)), results.get(i));
//		}
//	}
//	
//	private RestaurantDTO makeRestaurantDTO() {
//		LocationDTO location = new LocationDTO("Main St.", "123", "Sacramento", "California", 11111);
//		RestaurantDTO test = new RestaurantDTO("Lexi's Burgers", "Fast Food", location);
//		
//		FoodDTO food = new FoodDTO("Hamburger", 4.99, "A burger");
//		List<FoodDTO> foods = new ArrayList<FoodDTO>();
//		foods.add(food);
//		test.setFoods(foods);
//
//		return test;
//	}
//	
//	private Restaurant makeRestaurantEntity() {
//		Restaurant test = new Restaurant();
//		test.setName("Lexi's Burgers");
//
//		Location location = new Location();
//		location.setStreet("Main St.");
//		location.setUnit("123");
//		location.setCity("Sacramento");
//		location.setState("California");
//		location.setZipCode(95838);
//		test.setLocation(location);
//
//		test.setCuisine("Fast Food");
//		
//		Food food = new Food();
//		food.setName("Hamburger");
//		food.setPrice(4.99);
//		food.setDescription("A burger");
//		List<Food> foods = new ArrayList<Food>();
//		test.setFoods(foods);
//
//		return test;
//	}
//}
