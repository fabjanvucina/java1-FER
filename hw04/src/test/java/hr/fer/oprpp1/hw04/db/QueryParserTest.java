package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertTrue;     
import static org.junit.jupiter.api.Assertions.assertFalse; 
import static org.junit.jupiter.api.Assertions.assertThrows; 
import static org.junit.jupiter.api.Assertions.assertEquals; 


import org.junit.jupiter.api.Test;

public class QueryParserTest {

	@Test
	public void testIsDirectQuery() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		QueryParser qp2 = new QueryParser(" firstName =\"Ivo\" ");
		QueryParser qp3 = new QueryParser(" jmbag <\"0123456789\" ");
		QueryParser qp4 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
		assertTrue(qp1.isDirectQuery());
		assertFalse(qp2.isDirectQuery());
		assertFalse(qp3.isDirectQuery());
		assertFalse(qp4.isDirectQuery());
	}
	
	@Test
	public void testGetQueriedJmbag() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		QueryParser qp2 = new QueryParser(" firstName =\"Ivo\" ");
		
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
	}
	
	@Test
	public void testGetQuery() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" aNd lastName>\"J\"");
		QueryParser qp3 = new QueryParser("firstName LIKE \"I*a\"");
		
		
		ConditionalExpression ce1 = new ConditionalExpression(FieldValueGetters.JMBAG, "0123456789", ComparisonOperators.EQUALS);
		ConditionalExpression ce2 = new ConditionalExpression(FieldValueGetters.LAST_NAME, "J", ComparisonOperators.GREATER);
		ConditionalExpression ce3 = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "I*a", ComparisonOperators.LIKE);
		
		assertEquals(1, qp1.getQuery().size());
		assertEquals(2, qp2.getQuery().size());
		assertEquals(1, qp3.getQuery().size());
		
		assertEquals(ce1.getFieldGetter(), qp1.getQuery().get(0).getFieldGetter());
		assertEquals(ce1.getStringLiteral(), qp1.getQuery().get(0).getStringLiteral());
		assertEquals(ce1.getComparisonOperator(), qp1.getQuery().get(0).getComparisonOperator());
		
		assertEquals(ce1.getFieldGetter(), qp2.getQuery().get(0).getFieldGetter());
		assertEquals(ce1.getStringLiteral(), qp2.getQuery().get(0).getStringLiteral());
		assertEquals(ce1.getComparisonOperator(), qp2.getQuery().get(0).getComparisonOperator());
		
		assertEquals(ce2.getFieldGetter(), qp2.getQuery().get(1).getFieldGetter());
		assertEquals(ce2.getStringLiteral(), qp2.getQuery().get(1).getStringLiteral());
		assertEquals(ce2.getComparisonOperator(), qp2.getQuery().get(1).getComparisonOperator());
		
		assertEquals(ce3.getFieldGetter(), qp3.getQuery().get(0).getFieldGetter());
		assertEquals(ce3.getStringLiteral(), qp3.getQuery().get(0).getStringLiteral());
		assertEquals(ce3.getComparisonOperator(), qp3.getQuery().get(0).getComparisonOperator());
	}
}
