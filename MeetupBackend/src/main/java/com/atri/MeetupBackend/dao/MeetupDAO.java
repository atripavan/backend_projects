package com.atri.MeetupBackend.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.atri.MeetupBackend.entity.Meetup;
import com.atri.MeetupBackend.entity.MeetupAttendee;
import com.atri.UserBackend.entity.User;

public interface MeetupDAO {

	public List<Meetup> findAll();
	
	public Meetup findById(int id);
	
	public void save(Meetup emp);
	
	public void deleteById(int id);

	public List<Meetup> findByUserId(int id);

	void saveMeetupAttendees(List<MeetupAttendee> attnds);
	
	public User findUserById(int id);
	

}
