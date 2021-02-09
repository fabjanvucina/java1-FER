package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertTrue;    
import static org.junit.jupiter.api.Assertions.assertFalse; 
import static org.junit.jupiter.api.Assertions.assertThrows; 

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLess() {
		assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLessOrEquals() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Jasna"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testGreater() {
		assertTrue(ComparisonOperators.GREATER.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Jasna", "Ana"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	public void testEquals() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("Ana", "Ana"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testNotEquals() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Jasna"));
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testLike() {
		assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AAAA"));
		assertThrows(IllegalArgumentException.class, () -> ComparisonOperators.LIKE.satisfied("Zagreb", "Aba**"));
	}
	
}
