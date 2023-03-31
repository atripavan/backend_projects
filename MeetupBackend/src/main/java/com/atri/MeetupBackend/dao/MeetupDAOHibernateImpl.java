package com.atri.MeetupBackend.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atri.MeetupBackend.entity.Meetup;
import com.atri.MeetupBackend.entity.MeetupAttendee;
import com.atri.UserBackend.entity.User;

@Repository
public class MeetupDAOHibernateImpl implements MeetupDAO {


	//define field for entitymanager
	private EntityManager entityManager;
	
	//setup constructor injection
	
	@Autowired
	public MeetupDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Meetup> findAll() {
		Session sess = entityManager.unwrap(Session.class);
		
		Query<Meetup> qry = sess.createQuery(" from Meetup", Meetup.class);
		
		return qry.list();
	}

	@Override
	public Meetup findById(int id) {
		Session sess = entityManager.unwrap(Session.class);
		return sess.get(Meetup.class, id);
	}

	@Override
	public void save(Meetup m) {
		Session sess = entityManager.unwrap(Session.class);
		sess.saveOrUpdate(m);
	}

	@Override
	public void deleteById(int id) {
		Session sess = entityManager.unwrap(Session.class);
		Query dQuery = 
				sess.createQuery("delete from Meetup where id=:id");
		dQuery.setParameter("id", id);
		dQuery.executeUpdate();		
	}

	@Override
	public List<Meetup> findByUserId(int userId){
		Session sess = entityManager.unwrap(Session.class);
		
		Query<Meetup> qry = sess.createQuery(" from Meetup m where m.userId = :userId", Meetup.class);
		qry.setParameter("userId", userId);		
		return qry.list();
		
	}

	@Override
	public void saveMeetupAttendees(List<MeetupAttendee> attnds) {
		Session sess = entityManager.unwrap(Session.class);
		sess.saveOrUpdate(attnds);
	}

	@Override
	public User findUserById(int id) {
		Session sess = entityManager.unwrap(Session.class);
		
		Query<User> qry = sess.createQuery(" from User u where u.id = :id", User.class);
		qry.setParameter("id", id);		
		return qry.uniqueResult();
	}

}
