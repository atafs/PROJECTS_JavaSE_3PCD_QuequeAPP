/** 
 * americoLIB concept 
 * @author Americo Tomas (atafs): 12/03/2015 
 */

package dao.phonebook_arraylist;

public class Contact {

	// ATTRIBUTES
	private int number;
	private String name;

	// CONSTRUCTOR
	public Contact(String name, int number) {
		this.number = number;
		this.name = name;
	}

	// GETTERS AND SETTERS
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//TOSTRING
	@Override
	public String toString() {
		String toReturn = "";
		toReturn += "NAME: " + name + "\n";
		toReturn += "NUMBER: " + number + "\n";
		return toReturn;
	}

}
