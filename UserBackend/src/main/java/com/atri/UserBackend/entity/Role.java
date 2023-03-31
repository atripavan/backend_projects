package com.atri.UserBackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name="role_name" ,length = 20)
	private ERole roleName;

	public Role() {

	}

	public Role(ERole name) {
		this.roleName = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getRoleName() {
		return roleName;
	}

	public void setRoleName(ERole rolename) {
		this.roleName = rolename;
	}
}