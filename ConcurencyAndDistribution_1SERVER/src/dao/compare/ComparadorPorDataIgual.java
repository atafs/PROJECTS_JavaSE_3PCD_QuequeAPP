package dao.compare;

import java.util.Comparator;

import dao.Contact;

public class ComparadorPorDataIgual implements Comparator<Contact>{

	@Override
	public int compare(Contact c1, Contact c2) {
		ComparadorPorNome compNome = new ComparadorPorNome();
		ComparadorPorData compData = new ComparadorPorData();
		
		if(compData.compare(c1, c2) != 0){
			return compData.compare(c1, c2);
		
		} else {
			return compNome.compare(c1, c2);
		}
	}

}
