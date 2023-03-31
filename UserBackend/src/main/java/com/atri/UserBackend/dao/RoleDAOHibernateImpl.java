package com.atri.UserBackend.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atri.UserBackend.entity.Role;

@Repository
public class RoleDAOHibernateImpl implements RoleDAO {


	//define field for entitymanager
	private EntityManager entityManager;
	
	//setup constructor injection
	
	@Autowired
	public RoleDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Role> findAll() {
		Session sess = entityManager.unwrap(Session.class);
		
		Query<Role> qry = sess.createQuery(" from Role", Role.class);
		
		return qry.list();
	}

	@Override
	public Role findById(int id) {
		Session sess = entityManager.unwrap(Session.class);
		return sess.get(Role.class, id);
	}

	@Override
	public void save(Role r) {
		Session sess = entityManager.unwrap(Session.class);
		sess.saveOrUpdate(r);
	}

	@Override
	public void deleteById(int id) {
		Session sess = entityManager.unwrap(Session.class);
		Query dQuery = 
				sess.createQuery("delete from Role where id=:id");
		dQuery.setParameter("id", id);
		dQuery.executeUpdate();		
	}

}
