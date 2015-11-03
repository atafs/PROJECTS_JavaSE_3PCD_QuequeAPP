package com.iscte.queque.client.list;

import java.util.PriorityQueue;
import java.util.Queue;

import com.iscte.queque.client.serializable.ChatMessage;

public class ManageQueue {
	
	//ATTRIBUTES
	private Queue<ChatMessage> listMessages = new PriorityQueue<ChatMessage>();

	//CONSTRUCTOR
	public ManageQueue(Queue<ChatMessage> listMessages) {
		this.listMessages = listMessages;
	}

	//GETTERS AND SETTERS
	public Queue<ChatMessage> getListMessages() {
		return listMessages;
	}

	public void setListMessages(Queue<ChatMessage> listMessages) {
		this.listMessages = listMessages;
	}
	
	//TOSTRING
	@Override
	public String toString() {
		return super.toString();
	}
	
	

	

}