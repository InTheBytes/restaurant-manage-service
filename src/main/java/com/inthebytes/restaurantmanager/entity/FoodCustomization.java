package com.inthebytes.restaurantmanager.entity;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class FoodCustomization {
	
	@EmbeddedId
	FoodCustomizationKey id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("customizationId")
	@JoinColumn(name = "customization_id")
	private CustomizationModel customization;

	@ManyToOne
	@MapsId("foodId")
	@JoinColumn(name = "food_id")
	private FoodModel food;
	
	private Double charge;

	public FoodCustomizationKey getId() {
		return id;
	}

	public void setId(FoodCustomizationKey id) {
		this.id = id;
	}

	public CustomizationModel getCustomization() {
		return customization;
	}

	public void setCustomization(CustomizationModel customization) {
		this.customization = customization;
	}

	public FoodModel getFood() {
		return food;
	}

	public void setFood(FoodModel food) {
		this.food = food;
	}

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodCustomization other = (FoodCustomization) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
