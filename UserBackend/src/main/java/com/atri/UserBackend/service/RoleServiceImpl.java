package com.atri.UserBackend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.atri.UserBackend.dao.RoleDAO;
import com.atri.UserBackend.entity.Role;

public class RoleServiceImpl implements RoleService {

	private RoleDAO roleDAO;

	@Autowired
	public RoleServiceImpl(RoleDAO eDao) {
		roleDAO = eDao;
	}
	@Override
	@Transactional
	public List<Role> findAll() {

		return roleDAO.findAll();
	}

	@Override
	@Transactional
	public Role findById(int id) {
		return roleDAO.findById(id);
	}

	@Override
	@Transactional
	public void save(Role r) {
		roleDAO.save(r);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		roleDAO.deleteById(id);
	}

}
