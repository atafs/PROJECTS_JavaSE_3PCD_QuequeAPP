package com.iscte.queque._4serializable._v12_wait_offline.message;

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
	
	//ACTIONSTATE
	private ActionState onOfState;

	//CONSTRUCTOR
	public Message(String fromUser, String message) {
		this.fromUser = fromUser;
		ToUser = new ArrayList<String>();
		this.message = message;
		this.onOfState = ActionState.ONLINE;
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
	
	public ActionState getOnOfState() {
		return onOfState;
	}

	public void setOnOfState(ActionState onOfState) {
		this.onOfState = onOfState;
	}

	//INNER CLASS: ENUM
	public static enum ActionState{
		ONLINE, OFFLINE;

		//CONSTRUCTOR
		private ActionState() {}
	}

}
