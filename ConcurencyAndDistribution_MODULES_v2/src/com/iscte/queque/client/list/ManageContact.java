package com.iscte.queque.client.list;

import java.util.ArrayList;
import java.util.List;

import com.iscte.queque.client.list.dao.Contact;

public class ManageContact {
	
	//ATTRIBUTES
	private List<Contact> listContacts = new ArrayList<Contact>();

	//CONSTRUCTOR
	public ManageContact(List<Contact> listContacts) {
		this.listContacts = listContacts;
	}

	//GETTERS AND SETTERS
	public List<Contact> getListMessages() {
		return listContacts;
	}

	public void setListMessages(List<Contact> listContacts) {
		this.listContacts = listContacts;
	}
	
	//TOSTRING
	@Override
	public String toString() {
		return super.toString();
	}

}
