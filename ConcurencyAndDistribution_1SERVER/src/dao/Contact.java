package dao;


public class Contact {

	private String name;
	private int number;
	private Data data;
	
	public Contact(String name, int number, Data data) {
		this.name = name;
		this.number = number;
		this.data = data;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Data getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return name + " -> " + number + " - " + data;
	}
}
