package com.inthebytes.restaurantmanager.dto;

import javax.persistence.Id;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LocationDTO {

	@Id
	@Nullable
	@JsonIgnore
	private Long locationId;
	
	@NonNull
	private String street;
	
	@NonNull
	private String unit;
	
	@NonNull
	private String city;
	
	@NonNull
	private String state;
	
	@NonNull
	private Integer zipCode;

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
}
