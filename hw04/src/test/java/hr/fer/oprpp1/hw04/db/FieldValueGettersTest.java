package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {

	@Test
	public void testFirstName() {
		StudentRecord student = new StudentRecord("0000000001", "Ivić", "Ivo", "5");
		assertEquals("Ivo", FieldValueGetters.FIRST_NAME.get(student));
	}
	
	@Test
	public void testLastName() {
		StudentRecord student = new StudentRecord("0000000001", "Ivić", "Ivo", "5");
		assertEquals("Ivić", FieldValueGetters.LAST_NAME.get(student));
	}
	
	@Test
	public void testJmbag() {
		StudentRecord student = new StudentRecord("0000000001", "Ivić", "Ivo", "5");
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(student));
	}
}
