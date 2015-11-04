package com.iscte.queque.client.serializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.iscte.queque.client.dao.Contact;

public class ChatMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String text;
	private String nameReserved;
	
	//CONTANTS
	private Action action;
	private Action state;
	
	//LISTS
	private Set<String> setOnlines;
	
	public ChatMessage() {
		this.setOnlines = new HashSet<String>();
	}

	public String getName() {
		return this.name;
	}

	public String getText() {
		return this.text;
	}

	public String getNameReserved() {
		return this.nameReserved;
	}

	public Set<String> getSetOnlines() {
		return this.setOnlines;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setNameReserved(String nameReserved) {
		this.nameReserved = nameReserved;
	}

	public void setSetOnlines(Set<String> setOnlines) {
		this.setOnlines = setOnlines;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getState() {
		return state;
	}

	public void setState(Action state) {
		this.state = state;
	}

	public static enum Action {
		CONNECT, DISCONNECT, SEND_ONE, SEND_ALL, USERS_ONLINE, ONLINE, OFFLINE;

		private Action() {
		}
	}
}
