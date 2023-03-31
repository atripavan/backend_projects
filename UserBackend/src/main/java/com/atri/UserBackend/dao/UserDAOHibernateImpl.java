package com.atri.UserBackend.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atri.UserBackend.entity.User;

@Repository
public class UserDAOHibernateImpl implements UserDAO {


	//define field for entitymanager
	private EntityManager entityManager;
	
	//setup constructor injection
	
	@Autowired
	public UserDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<User> findAll() {
		Session sess = entityManager.unwrap(Session.class);
		
		Query<User> qry = sess.createQuery(" from User", User.class);
		
		return qry.list();
	}

	@Override
	public User findById(int id) {
		Session sess = entityManager.unwrap(Session.class);
		return sess.get(User.class, id);
	}

	@Override
	public void save(User m) {
		Session sess = entityManager.unwrap(Session.class);
		sess.saveOrUpdate(m);
	}

	@Override
	public void deleteById(int id) {
		Session sess = entityManager.unwrap(Session.class);
		Query dQuery = 
				sess.createQuery("delete from User where id=:id");
		dQuery.setParameter("id", id);
		dQuery.executeUpdate();		
	}

	@Override
	public User findByEmail(String email) {
		Session sess = entityManager.unwrap(Session.class);
		
		Query<User> qry = sess.createQuery(" from User u where u.email = :email", User.class);
		qry.setParameter("email", email);
		
		return qry.uniqueResult();
	}

}
