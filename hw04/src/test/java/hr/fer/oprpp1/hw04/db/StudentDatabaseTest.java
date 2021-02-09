package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	@Test
	public void testForJmbag() {
		List<String> studentStrings = new ArrayList<>();
		studentStrings.add("0000000001	Akšamović	Marin	2");
		studentStrings.add("0000000002	Bakamović	Petra	3");
		studentStrings.add("0000000003	Bosnić	Andrea	4");
		studentStrings.add("0000000004	Božić	Marin	5");
		studentStrings.add("0000000005	Brezović	Jusufadis	2");
		
		StudentDatabase studentsDB = new StudentDatabase(studentStrings);
		
		assertEquals(new StudentRecord("0000000001", "Akšamović", "Marin", "2"), studentsDB.forJMBAG("0000000001"));
		assertNull(studentsDB.forJMBAG("0000000006"));
	}
	
	@Test
	public void testFilter() {
		List<String> studentStrings = new ArrayList<>();
		studentStrings.add("0000000001	Akšamović	Marin	2");
		studentStrings.add("0000000002	Bakamović	Petra	3");
		studentStrings.add("0000000003	Bosnić	Andrea	4");
		studentStrings.add("0000000004	Božić	Marin	5");
		studentStrings.add("0000000005	Brezović	Jusufadis	2");
		
		StudentDatabase studentsDB = new StudentDatabase(studentStrings);
		
		IFilter filterAlwaysTrue = student -> true;
		IFilter filterAlwaysFalse = student -> false;
		
		assertEquals(5, studentsDB.filter(filterAlwaysTrue).size());
		assertEquals(0, studentsDB.filter(filterAlwaysFalse).size());		
	}
}
