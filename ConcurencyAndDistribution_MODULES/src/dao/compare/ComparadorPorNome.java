package dao.compare;

import java.util.Comparator;

import dao.Contact;

public class ComparadorPorNome implements Comparator<Contact>{

	@Override
	public int compare(Contact c1, Contact c2) {
		return c1.getName().compareTo(c2.getName());
	}

}
