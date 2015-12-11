package com.iscte.queque.client.test.contact;

import java.util.ArrayList;
import java.util.List;

import com.iscte.queque.client.serializable.ChatMessage;

public class Main {
	
	//ATTRIBUTES
	private static List<Contact> listContacts = new ArrayList<Contact>();

	public static void main(String[] args) {

		//INSTANCIATE ###############################
		//CONTACTS
		Contact atafs = new Contact("atafs");
		atafs.setName("myatafs");
		atafs.setMessagesToReceive(1);
		
		Contact atomas = new Contact("atomas");
		atafs.setName("myatomas");
		atafs.setMessagesToReceive(1);
		
		Contact ana = new Contact("ana");
		atafs.setName("myana");
		atafs.setMessagesToReceive(1);
		
		//CHAT MESSAGE
		ChatMessage message1 = new ChatMessage();
		message1.setNameReserved("atafs");
		
		//MESSAGE
		message1.setText("asdasdasdasd");
		atafs.getMessageQueue().add(message1.getText());
		atomas.getMessageQueue().add(message1.getText());
		ana.getMessageQueue().add(message1.getText());

		//ADD TO LIST
		listContacts.add(atafs);
		listContacts.add(atomas);
		listContacts.add(ana);
		
		//PRINT
		System.err.println("---------------PRINT--------------\n");
		for (Contact contact : listContacts) {
			for (String string : contact.getMessageQueue()) {
				System.out.println(contact.getName() + ": " + string);
			}
		}
	}
}
