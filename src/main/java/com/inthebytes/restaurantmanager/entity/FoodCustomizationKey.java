package com.inthebytes.restaurantmanager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FoodCustomizationKey implements Serializable {
	private static final long serialVersionUID = 445667783102853097L;

	public FoodCustomizationKey() {
		super();
	}

	public FoodCustomizationKey(Long customizationId, Long foodId) {
		super();
		this.customizationId = customizationId;
		this.foodId = foodId;
	}

	@Column(name = "customization_id")
	private Long customizationId;
	
	@Column(name = "food_id")
	private Long foodId;

	public Long getCustomizationId() {
		return customizationId;
	}

	public void setCustomizationId(Long customizationId) {
		this.customizationId = customizationId;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customizationId == null) ? 0 : customizationId.hashCode());
		result = prime * result + ((foodId == null) ? 0 : foodId.hashCode());
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
		FoodCustomizationKey other = (FoodCustomizationKey) obj;
		if (customizationId == null) {
			if (other.customizationId != null)
				return false;
		} else if (!customizationId.equals(other.customizationId))
			return false;
		if (foodId == null) {
			if (other.foodId != null)
				return false;
		} else if (!foodId.equals(other.foodId))
			return false;
		return true;
	}
}
