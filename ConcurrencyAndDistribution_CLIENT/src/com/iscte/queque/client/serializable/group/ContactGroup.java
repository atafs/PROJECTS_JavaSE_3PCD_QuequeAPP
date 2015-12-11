package com.iscte.queque.client.serializable.group;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.iscte.queque.client.serializable.Client;
import com.iscte.queque.client.serializable.group.contact.Contact;


//DAO
public class ContactGroup{

	// ATTRIBUTES
	private String groupName;
	private List<Contact> myContactGroups;
	private Queue<String> text;
	
	//CONSTRUCTOR
	public ContactGroup(String groupName, List<Contact> myContactGroups) {
		this.groupName = groupName;
		this.myContactGroups = myContactGroups;
		this.text = new PriorityQueue<String>();
	}
	
	public ContactGroup(String groupName) {
		this.groupName = groupName;
		this.myContactGroups = new ArrayList<Contact>();
		this.text = new PriorityQueue<String>();
	}
	
	public ContactGroup() {
		this.groupName = "";
		this.myContactGroups = new ArrayList<Contact>();
		this.text = new PriorityQueue<String>();
	}

	//GETTERS AND SETTERS
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Contact> getMyContactGroups() {
		return myContactGroups;
	}

	public void setMyContactGroups(List<Contact> myContactGroups) {
		this.myContactGroups = myContactGroups;
	}

	public Queue<String> getText() {
		return text;
	}

	public void setText(Queue<String> text) {
		this.text = text;
	}
}
