package com.atri.UserBackend.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.atri.UserBackend.entity.User;

public interface UserService extends UserDetailsService {

	public List<User> findAll();
	
	public User findById(int id);
	
	public void save(User emp);
	
	public void deleteById(int id);

	User getUserByEmail(String email) throws UsernameNotFoundException;

}
