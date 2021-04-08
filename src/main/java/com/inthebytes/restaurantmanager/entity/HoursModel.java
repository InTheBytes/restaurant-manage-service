package com.inthebytes.restaurantmanager.entity;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hours")
public class HoursModel implements Serializable {

	private static final long serialVersionUID = 3660388358750749224L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long hoursId;

	@Column(name = "monday_open", columnDefinition = "TIME")
	public LocalTime mondayOpen;
	
	@Column(name = "monday_close", columnDefinition = "TIME")
	public LocalTime mondayClose;
	
	@Column(name = "tuesday_open", columnDefinition = "TIME")
	public LocalTime tuesdayOpen;
	
	@Column(name = "tuesday_close", columnDefinition = "TIME")
	public LocalTime tuesdayClose;
	
	@Column(name = "wednesday_open", columnDefinition = "TIME")
	public LocalTime wednesdayOpen;
	
	@Column(name = "wednesday_close", columnDefinition = "TIME")
	public LocalTime wednesdayClose;
	
	@Column(name = "thursday_open", columnDefinition = "TIME")
	public LocalTime thursdayOpen;
	
	@Column(name = "thursday_close", columnDefinition = "TIME")
	public LocalTime thursdayClose;
	
	@Column(name = "friday_open", columnDefinition = "TIME")
	public LocalTime fridayOpen;
	
	@Column(name = "friday_close", columnDefinition = "TIME")
	public LocalTime fridayClose;
	
	@Column(name = "saturday_open", columnDefinition = "TIME")
	public LocalTime saturdayOpen;
	
	@Column(name = "saturday_close", columnDefinition = "TIME")
	public LocalTime saturdayClose;
	
	@Column(name = "sunday_open", columnDefinition = "TIME")
	public LocalTime sundayOpen;
	
	@Column(name = "sunday_close", columnDefinition = "TIME")
	public LocalTime sundayClose;

	public Long getHoursId() {
		return hoursId;
	}

	public void setHoursId(Long hoursId) {
		this.hoursId = hoursId;
	}

	public LocalTime getMondayOpen() {
		return mondayOpen;
	}

	public void setMondayOpen(LocalTime mondayOpen) {
		this.mondayOpen = mondayOpen;
	}

	public LocalTime getMondayClose() {
		return mondayClose;
	}

	public void setMondayClose(LocalTime mondayClose) {
		this.mondayClose = mondayClose;
	}

	public LocalTime getTuesdayOpen() {
		return tuesdayOpen;
	}

	public void setTuesdayOpen(LocalTime tuesdayOpen) {
		this.tuesdayOpen = tuesdayOpen;
	}

	public LocalTime getTuesdayClose() {
		return tuesdayClose;
	}

	public void setTuesdayClose(LocalTime tuesdayClose) {
		this.tuesdayClose = tuesdayClose;
	}

	public LocalTime getWednesdayOpen() {
		return wednesdayOpen;
	}

	public void setWednesdayOpen(LocalTime wednesdayOpen) {
		this.wednesdayOpen = wednesdayOpen;
	}

	public LocalTime getWednesdayClose() {
		return wednesdayClose;
	}

	public void setWednesdayClose(LocalTime wednesdayClose) {
		this.wednesdayClose = wednesdayClose;
	}

	public LocalTime getThursdayOpen() {
		return thursdayOpen;
	}

	public void setThursdayOpen(LocalTime thursdayOpen) {
		this.thursdayOpen = thursdayOpen;
	}

	public LocalTime getThursdayClose() {
		return thursdayClose;
	}

	public void setThursdayClose(LocalTime thursdayClose) {
		this.thursdayClose = thursdayClose;
	}

	public LocalTime getFridayOpen() {
		return fridayOpen;
	}

	public void setFridayOpen(LocalTime fridayOpen) {
		this.fridayOpen = fridayOpen;
	}

	public LocalTime getFridayClose() {
		return fridayClose;
	}

	public void setFridayClose(LocalTime fridayClose) {
		this.fridayClose = fridayClose;
	}

	public LocalTime getSaturdayOpen() {
		return saturdayOpen;
	}

	public void setSaturdayOpen(LocalTime saturdayOpen) {
		this.saturdayOpen = saturdayOpen;
	}

	public LocalTime getSaturdayClose() {
		return saturdayClose;
	}

	public void setSaturdayClose(LocalTime saturdayClose) {
		this.saturdayClose = saturdayClose;
	}

	public LocalTime getSundayOpen() {
		return sundayOpen;
	}

	public void setSundayOpen(LocalTime sundayOpen) {
		this.sundayOpen = sundayOpen;
	}

	public LocalTime getSundayClose() {
		return sundayClose;
	}

	public void setSundayClose(LocalTime sundayClose) {
		this.sundayClose = sundayClose;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hoursId == null) ? 0 : hoursId.hashCode());
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
		HoursModel other = (HoursModel) obj;
		if (hoursId == null) {
			if (other.hoursId != null)
				return false;
		} else if (!hoursId.equals(other.hoursId))
			return false;
		return true;
	}
}
