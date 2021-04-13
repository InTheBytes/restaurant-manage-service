package com.inthebytes.restaurantmanager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class ManagerRole implements Serializable {
	
	private static final long serialVersionUID = 8997431671030073287L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "name")
	private String name;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer userId) {
		this.roleId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
