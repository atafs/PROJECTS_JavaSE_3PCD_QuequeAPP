package dao.compare;

import java.util.Comparator;

import dao.Contact;

public class ComparadorPorData implements Comparator<Contact>{

	@Override
	public int compare(Contact c1, Contact c2) {
		return c1.getData().compareTo(c2.getData());
	}

}
