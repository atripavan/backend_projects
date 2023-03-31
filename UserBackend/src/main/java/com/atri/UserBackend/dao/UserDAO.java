package com.atri.UserBackend.dao;

import java.util.List;

import com.atri.UserBackend.entity.User;

public interface UserDAO {

	public List<User> findAll();
	
	public User findById(int id);
	
	public void save(User usr);
	
	public void deleteById(int id);

	public User findByEmail(String email);
	

}
