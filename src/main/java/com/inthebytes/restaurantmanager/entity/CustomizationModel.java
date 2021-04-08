package com.inthebytes.restaurantmanager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customization")
public class CustomizationModel implements Serializable {

	private static final long serialVersionUID = -5716322102313101842L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customize_id")
	private Long customizeId;
	
	@Column(name = "description", nullable = false)
	private String description;

	public Long getCustomizeId() {
		return customizeId;
	}

	public void setCustomizeId(Long customizeId) {
		this.customizeId = customizeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customizeId == null) ? 0 : customizeId.hashCode());
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
		CustomizationModel other = (CustomizationModel) obj;
		if (customizeId == null) {
			if (other.customizeId != null)
				return false;
		} else if (!customizeId.equals(other.customizeId))
			return false;
		return true;
	}
}
