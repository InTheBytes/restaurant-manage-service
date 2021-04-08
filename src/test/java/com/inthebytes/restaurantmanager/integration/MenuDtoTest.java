package com.inthebytes.restaurantmanager.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.dto.MenuDTO;
import com.inthebytes.restaurantmanager.entity.FoodModel;
import com.inthebytes.restaurantmanager.entity.MenuModel;

@DataJpaTest
public class MenuDtoTest {
	
	@Autowired
	MenuDTO menuRepo;
	
	@Autowired
	FoodDTO foodRepo;
	
	@Test
	public void emptyMenuTest() {
		MenuModel test = new MenuModel();
		test.setTitle("Classic Cuisines");
		
		menuRepo.save(test);
		assertThat(menuRepo.findAll().size()).isNotZero();
		assertThat(menuRepo.findByTitle("Classic Cuisines")).isNotNull();
		menuRepo.delete(test);
		assertThat(menuRepo.findAll().size()).isZero();
		assertThat(menuRepo.findByTitle("Classic Cuisines")).isNull();
	}
	
	@Test
	public void populatedMenuTest() {
		MenuModel test = new MenuModel();
		test.setTitle("Classic Cuisines");
		
		FoodModel food1 = new FoodModel();
		food1.setName("Hamburger");
		food1.setDescription("2 patty + bun");
		food1.setPrice(4.99);
		
		FoodModel food2 = new FoodModel();
		food2.setName("Pizza");
		food2.setDescription("Sauce and stuff on a dough disk");
		food2.setPrice(4.99);
		
		List<FoodModel> foods = new ArrayList<FoodModel> ();
		foods.add(food1);
		foods.add(food2);
		test.setMenuItems(foods);
		
		menuRepo.save(test);
		assertThat(menuRepo.findByTitle("Classic Cuisines")).isNotNull();
		assertThat(foodRepo.findAll().size()).isNotZero();
		menuRepo.delete(test);
		assertThat(menuRepo.findAll().size()).isZero();
		assertThat(foodRepo.findAll().size()).isZero();
	}

}
