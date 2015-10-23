package dao.test;

import java.util.ArrayList;
import java.util.Collections;

import dao.Contact;
import dao.Data;
import dao.compare.ComparadorPorDataIgual;

public class Main {

	public static void main(String[] args) {
		ArrayList<Contact> lista = new ArrayList<Contact>();
		
		lista.add(new Contact("Ana", 91, new Data(10, 3, 2015)));
		lista.add(new Contact("Maria", 96, new Data(12, 3, 2015)));
		lista.add(new Contact("João", 93, new Data(12, 3, 2015)));
		
		System.out.println(lista);
		
		Collections.sort(lista, new ComparadorPorDataIgual());
		
		System.out.println(lista);
	}
	
}
