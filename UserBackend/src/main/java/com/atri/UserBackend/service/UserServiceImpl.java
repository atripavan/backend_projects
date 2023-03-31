package com.atri.UserBackend.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atri.UserBackend.dao.UserDAO;
import com.atri.UserBackend.entity.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	BCryptPasswordEncoder bcryptEnc;
	
	@Autowired
	public UserServiceImpl(UserDAO eDao, BCryptPasswordEncoder bcryptEnc) {
		userDAO = eDao;
		this.bcryptEnc = bcryptEnc;
	}
	
	@Override
	@Transactional
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	@Transactional
	public User findById(int id) {
		return userDAO.findById(id);
	}

	@Override
	@Transactional
	public void save(User emp) {
		userDAO.save(emp);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		userDAO.deleteById(id);		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByEmail(username);
		if(user == null) throw new UsernameNotFoundException(username);
		
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, new ArrayList<>());
	}
	
	@Override
	public User getUserByEmail(String email) throws UsernameNotFoundException {
		User user = userDAO.findByEmail(email);
		if(user == null) throw new UsernameNotFoundException(email);
		
		return user;
	}

}
