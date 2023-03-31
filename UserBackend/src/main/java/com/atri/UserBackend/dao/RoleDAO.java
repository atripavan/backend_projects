package com.atri.UserBackend.dao;

import java.util.List;

import com.atri.UserBackend.entity.Role;

public interface RoleDAO {

	public List<Role> findAll();
	
	public Role findById(int id);
	
	public void save(Role r);
	
	public void deleteById(int id);
}
