package com.atri.MeetupBackend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atri.MeetupBackend.dao.MeetupDAO;
import com.atri.MeetupBackend.entity.Meetup;
import com.atri.MeetupBackend.entity.MeetupAttendee;

@Service
public class MeetupServiceImpl implements MeetupService {

	private MeetupDAO meetupDAO;
	
	@Autowired
	public MeetupServiceImpl(MeetupDAO eDao) {
		meetupDAO = eDao;
	}
	
	@Override
	@Transactional
	public List<Meetup> findAll() {
		return meetupDAO.findAll();
	}

	@Override
	@Transactional
	public Meetup findById(int id) {
		return meetupDAO.findById(id);
	}

	@Override
	@Transactional
	public void save(Meetup emp) {
		meetupDAO.save(emp);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		meetupDAO.deleteById(id);		
	}
	
	@Override
	@Transactional
	public List<Meetup> findByUserId(int userId) {
		return meetupDAO.findByUserId(userId);
	}

	@Override
	@Transactional
	public void saveMeetupAttendees(List<MeetupAttendee> attnds) {
		meetupDAO.saveMeetupAttendees(attnds);
	}

}
