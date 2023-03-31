package com.atri.MeetupBackend.service;

import java.util.List;

import com.atri.MeetupBackend.entity.Meetup;
import com.atri.MeetupBackend.entity.MeetupAttendee;

public interface MeetupService {

	public List<Meetup> findAll();
	
	public Meetup findById(int id);
	
	public void save(Meetup emp);
	
	public void deleteById(int id);

	public List<Meetup> findByUserId(int userId);

	void saveMeetupAttendees(List<MeetupAttendee> attnds);

}
