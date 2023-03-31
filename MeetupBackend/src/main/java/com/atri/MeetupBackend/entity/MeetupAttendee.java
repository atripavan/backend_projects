package com.atri.MeetupBackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.atri.MeetupBackend.entity.enums.AccessLevel;
import com.atri.UserBackend.entity.User;

@Entity
@Table(name="meetup_attendee")
public class MeetupAttendee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="meetup_id")
	private Meetup meetup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="access_level")
	private AccessLevel acessLevel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Meetup getMeetup() {
		return meetup;
	}

	public void setMeetup(Meetup meetup) {
		this.meetup = meetup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccessLevel getAcessLevel() {
		return acessLevel;
	}

	public void setAcessLevel(AccessLevel acessLevel) {
		this.acessLevel = acessLevel;
	}
	
	

}
