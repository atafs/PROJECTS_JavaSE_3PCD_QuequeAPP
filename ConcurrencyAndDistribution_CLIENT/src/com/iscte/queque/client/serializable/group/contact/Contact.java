package com.iscte.queque.client.serializable.group.contact;

import java.util.PriorityQueue;
import java.util.Queue;

import com.iscte.queque.client.serializable.ChatMessage.Action;
import com.iscte.queque.client.serializable.group.ContactGroup;

/** DAO */
public class Contact{

	// ATTRIBUTES
	private String name;
	private String nameReserved;
	private String myGroupName;
	//ENUM
	private Action state;
	//
	private int messagesToReceive;
	private Queue<String> messageQueue;

	// CONSTRUCTOR
	public Contact(String name, String myGroupName) {
		this.name = name;
		this.myGroupName = myGroupName;
		this.messagesToReceive = 0;
		this.messageQueue = new PriorityQueue<>();
	}

	// GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMessagesToReceive() {
		return messagesToReceive;
	}

	public void setMessagesToReceive(int messagesToReceive) {
		this.messagesToReceive = messagesToReceive;
	}

	public Queue<String> getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(Queue<String> messageQueue) {
		this.messageQueue = messageQueue;
	}

	public String getMyGroupName() {
		return myGroupName;
	}

	public void setMyGroupName(String myGroupName) {
		this.myGroupName = myGroupName;
	}
}
