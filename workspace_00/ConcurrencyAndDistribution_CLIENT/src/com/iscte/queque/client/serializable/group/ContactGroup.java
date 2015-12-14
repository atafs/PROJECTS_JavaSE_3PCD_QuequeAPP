package com.iscte.queque.client.serializable.group;

import java.util.ArrayList;
import java.util.List;

import com.iscte.queque.client.serializable.group.contact.Contact;


//DAO
public class ContactGroup {

	// ATTRIBUTES
	private String groupName;
	private List<Contact> myContactGroups;
	
	//CONSTRUCTOR
	public ContactGroup(String groupName, List<Contact> myContactGroups) {
		this.groupName = groupName;
		this.myContactGroups = myContactGroups;
	}
	
	public ContactGroup(String groupName) {
		this.groupName = groupName;
		this.myContactGroups = new ArrayList<Contact>();
	}
	
	public ContactGroup() {
		this.groupName = "";
		this.myContactGroups = new ArrayList<Contact>();
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
}