package com.inthebytes.restaurantmanager.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.inthebytes.restaurantmanager.dto.CustomizationDTO;
import com.inthebytes.restaurantmanager.dto.FoodDTO;
import com.inthebytes.restaurantmanager.entity.CustomizationModel;
import com.inthebytes.restaurantmanager.entity.FoodCustomization;
import com.inthebytes.restaurantmanager.entity.FoodCustomizationKey;
import com.inthebytes.restaurantmanager.entity.FoodModel;

@DataJpaTest
public class FoodAndCustomizationDtoTest {
	
	@Autowired
	private FoodDTO foodRepo;
	
	@Autowired
	private CustomizationDTO customizationRepo;
	
	@Test
	public void testFoodDto() {
		FoodModel test = new FoodModel();
		test.setName("Hamburger");
		test.setDescription("2 patty + bun");
		test.setPrice(4.99);
		
		foodRepo.save(test);
		assertThat(foodRepo.findByName("Hamburger")).isNotNull();
		foodRepo.delete(test);
		assertThat(foodRepo.findAll().size()).isZero();
	}
	
	@Test
	public void testCustomizationDto() {
		CustomizationModel test = new CustomizationModel();
		test.setDescription("Mayonnaise");
		
		customizationRepo.save(test);
		assertThat(customizationRepo.findByDescription("Mayonnaise")).isNotNull();
		
		customizationRepo.delete(test);
		assertThat(customizationRepo.findAll().size()).isZero();
	}
	
	@Test
	public void testFoodCustomizations() {
		FoodModel test = new FoodModel();
		test.setName("Hamburger");
		test.setDescription("2 patty + bun");
		test.setPrice(4.99);
		
		foodRepo.save(test);
		test.setFoodId(foodRepo.findByName("Hamburger").getFoodId());
		
		List<FoodCustomization> options = new ArrayList<>();
		FoodCustomization cust1 = new FoodCustomization();
		cust1.setCharge(1.49);
		cust1.setFood(foodRepo.findByName("Hamburger"));
		
		CustomizationModel custom1 = new CustomizationModel();
		custom1.setDescription("Pickles");
		customizationRepo.save(custom1);
		
		cust1.setId(new FoodCustomizationKey(foodRepo.findByName("Hamburger").getFoodId(),
				customizationRepo.findByDescription("Pickles").getCustomizeId()));
		cust1.setCustomization(customizationRepo.findByDescription("Pickles"));
		options.add(cust1);

		test.setCustomizations(options);
		foodRepo.save(test);
		
		assertThat(foodRepo.findByName("Hamburger")).isNotNull();
		assertThat(customizationRepo.findByDescription("Pickles")).isNotNull();
		
		test = foodRepo.findByName("Hamburger");
		assertThat(test.getCustomizations().size()).isNotZero();
		
		foodRepo.delete(test);
		customizationRepo.delete(custom1);
		assertThat(foodRepo.findAll().size()).isZero();
		assertThat(customizationRepo.findAll().size()).isZero();
	}

}
