/** 
 * americoLIB concept 
 * @author Americo Tomas (atafs): 12/03/2015 
 */

package control_2OO.Start_OO_week05.phonebook_hashmap;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PhoneBookTest {

	private PhoneBook contacts = new PhoneBookMap();

	@Before
	public void setUp() throws Exception {
		contacts.addEntry("BBB BBB", 123456789);
		contacts.addEntry("AAA AAA", 234567891);
		contacts.addEntry("ZZZ ZZZ", 345678912);
		contacts.addEntry("ABB BBB", 456789123);
		contacts.addEntry("BBB ABB", 567891234);
	}

	@Test
	public void testAddEntry() {
		PhoneBook newContacts = new PhoneBookMap();
		newContacts.addEntry("AAA AAA", 123456789); // first
		assertEquals(newContacts.getNumber("AAA AAA"), 123456789);
		assertEquals(newContacts.getName(123456789), "AAA AAA");
		newContacts.addEntry("CCC AAA", 234567891); // last
		assertEquals(newContacts.getNumber("CCC AAA"), 234567891);
		assertEquals(newContacts.getName(234567891), "CCC AAA");
		newContacts.addEntry("BBB AAA", 345678912); // miidle
		assertEquals(newContacts.getNumber("BBB AAA"), 345678912);
		assertEquals(newContacts.getName(345678912), "BBB AAA");
	}

	@Test
	public void testGetName() {
		assertEquals(contacts.getName(123456789), "BBB BBB");
		assertEquals(contacts.getName(234567891), "AAA AAA");
		assertEquals(contacts.getName(345678912), "ZZZ ZZZ");
	}

	@Test
	public void testGetNumber() {
		assertEquals(contacts.getNumber("AAA AAA"), 234567891);
		assertEquals(contacts.getNumber("BBB BBB"), 123456789);
		assertEquals(contacts.getNumber("ZZZ ZZZ"), 345678912);
	}

	@Test
	public void testRemove() {
		assertTrue(contacts.contains(234567891));
		contacts.remove("AAA AAA");
		assertFalse(contacts.contains(234567891));
	}

	@Test
	public void testContains() {
		assertTrue(contacts.contains(123456789));
		assertFalse(contacts.contains(678912345));
	}

}
