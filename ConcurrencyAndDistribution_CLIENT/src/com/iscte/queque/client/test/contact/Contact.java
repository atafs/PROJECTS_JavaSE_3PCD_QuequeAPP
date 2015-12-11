package com.iscte.queque.client.test.contact;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.iscte.queque.client.serializable.ChatMessage;

/** DAO */
public class Contact {

	// ATTRIBUTES
	private String name;
	private String nameReserved;
	// COUNTER
	private int messagesToReceive;
	// LIST
	private Queue<String> messageQueue;
	private List<ChatMessage> messagesReceived;

	// CONSTRUCTOR
	public Contact(String name) {
		this.name = name;
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
}
