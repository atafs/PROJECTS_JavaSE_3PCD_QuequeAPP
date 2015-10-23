/** 
 * americoLIB concept 
 * @author Americo Tomas (atafs): 12/03/2015 
 */

package dao.phonebook_arraylist;

import java.util.ArrayList;

public class PhoneBookList implements PhoneBook {

	// ATTRIBUTES (COLLECTIONS)
	private Contact c;
	private ArrayList<Contact> contacts;

	// CONSTRUCTOR
	public PhoneBookList() {
		contacts = new ArrayList<Contact>();
	}

	//INTERFACE METHODS
	@Override
	public void addEntry(String name, int number) {
		c = new Contact(name, number);
		contacts.add(c);

	}

	@Override
	public String getName(int number) {
		for (Contact contact : contacts) {
			if (contact.getNumber() == number) {
				return contact.getName();
			}
		}
		return "";
	}

	@Override
	public int getNumber(String name) {
		for (Contact contact : contacts) {
			if (contact.getName() == name) {
				return contact.getNumber();
			}
		}
		return -1;
	}

	@Override
	public void remove(String name) {

		//METHOD1
//		ArrayList<Contact> aux = new ArrayList<Contact>();
//		for (Contact contact : contacts) {
//			if (!contact.getName().equals(name)) {
//				aux.add(contact);
//			}
//		}
		
		//METHOD2
		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getName().equals(name)) {
				contacts.remove(i);
				break;
			}
		}
	}

	@Override
	public boolean contains(int number) {

		for (Contact contact : contacts) {
			if (contact.getNumber() == (number)) {
				return true;
			}
		}
		return false;
	}

	//TOSTRING
	@Override
	public String toString() {
		String toReturn = "";
		
		for (Contact contact : contacts) {
			toReturn += "CONTACT: " + contact.toString() + "\n";
		}
		
		return toReturn;
	}

}
