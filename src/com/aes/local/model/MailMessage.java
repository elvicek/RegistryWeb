package com.aes.local.model;

import java.util.List;

import com.aes.data.domain.User;

public class MailMessage {
	
	private List<User> users;
	private String message;
	private String subject;
	
	
	
	public MailMessage(List<User> users, String message, String subject) {
		super();
		this.users = users;
		this.message = message;
		this.subject = subject;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

}
