package com.iscte.queque.client.list;

import java.util.ArrayList;
import java.util.List;

import com.iscte.queque.client.list.dao.Contact;

public class ManageContact {
	
	//ATTRIBUTES
	private String name;
	private List<Contact> listContacts = new ArrayList<Contact>();

	//CONSTRUCTOR
	public ManageContact(String name, List<Contact> listContacts) {
		this.name = name;
		this.listContacts = listContacts;
	}

	//GETTERS AND SETTERS	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Contact> getListContacts() {
		return listContacts;
	}

	public void setListContacts(List<Contact> listContacts) {
		this.listContacts = listContacts;
	}

	//TOSTRING
	@Override
	public String toString() {
		return super.toString();
	}

}
