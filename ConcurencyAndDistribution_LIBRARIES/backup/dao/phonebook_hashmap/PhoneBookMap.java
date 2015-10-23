/** 
 * americoLIB concept 
 * @author Americo Tomas (atafs): 12/03/2015 
*/

package control_2OO.Start_OO_week05.phonebook_hashmap;

import java.util.HashMap;
import java.util.Map;


public class PhoneBookMap implements PhoneBook {

	//ATTRIBUTES (COLLECTIONS)
	private Map<String, Integer> mapNameToNumber;
	private Map<Integer, String> mapNumberToName;

	public PhoneBookMap() {
		mapNameToNumber = new HashMap<String, Integer>();
		mapNumberToName = new HashMap<Integer, String>();
	}

	@Override
	public void addEntry(String name, int number) {
		mapNameToNumber.put(name,  number);
		mapNumberToName.put(number,  name);
	}

	@Override
	public String getName(int number) {
		return mapNumberToName.get(number).toString();
	}

	@Override
	public int getNumber(String name) {
		return mapNameToNumber.get(name);
	}

	@Override
	public void remove(String name) {
		mapNumberToName.remove(getNumber(name));
		mapNameToNumber.remove(name);
	}

	@Override
	public boolean contains(int number) {
		return mapNumberToName.containsKey(number);
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		
		//Set<String> set = mapNameToNumber.keySet();
		
		//DEVOLVE UMA LISTA NAO ORDENADA
		for (String key : mapNameToNumber.keySet()) {
			toReturn += key + " -> " + mapNameToNumber.get(key) + "\n";
		}
		
		return toReturn;
	}

}
