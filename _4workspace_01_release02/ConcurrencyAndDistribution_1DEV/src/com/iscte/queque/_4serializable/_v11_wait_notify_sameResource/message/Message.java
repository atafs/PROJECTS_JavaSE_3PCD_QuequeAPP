package com.iscte.queque._4serializable._v11_wait_notify_sameResource.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//DAO
public class Message implements Serializable{
	
	/* ATTRIBUTES */
	private static final long serialVersionUID = 1L;
	
	//USERS
	private String fromUser;
	private List<String >ToUser;

	//MESSAGES
	private String message;

	//CONSTRUCTOR
	public Message(String fromUser, String message) {
		this.fromUser = fromUser;
		ToUser = new ArrayList<String>();
		this.message = message;
	}

	//GETTERS AND SETTERS
	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public List<String> getToUser() {
		return ToUser;
	}

	public void setToUser(List<String> toUser) {
		ToUser = toUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
