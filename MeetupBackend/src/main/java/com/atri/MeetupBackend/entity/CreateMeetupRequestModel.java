package com.atri.MeetupBackend.entity;

import java.util.List;

public class CreateMeetupRequestModel {

	private int id;
	
	private String name;
	
	private String address;
	
	private String description;
	
	private String imgUrl;
	
	private int userId;
	
	private List<Integer> meetupUsers;
	
	public CreateMeetupRequestModel() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Integer> getMeetupUsers() {
		return meetupUsers;
	}

	public void setMeetupUsers(List<Integer> meetupUsers) {
		this.meetupUsers = meetupUsers;
	}
	
}
