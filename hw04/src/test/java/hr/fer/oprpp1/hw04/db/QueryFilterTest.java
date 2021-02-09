package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse; 
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	@Test
	public void testAccepts() {
		List<ConditionalExpression> conditionalExpressions = new ArrayList<>();
		
		ConditionalExpression ec1 = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "I*a", ComparisonOperators.LIKE);
		ConditionalExpression ec2 = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Horv*", ComparisonOperators.LIKE);
		
		conditionalExpressions.add(ec1);
		conditionalExpressions.add(ec2);
		
		QueryFilter qf = new QueryFilter(conditionalExpressions);
		
		StudentRecord sr1 = new StudentRecord("0000000001", "Ivić", "Iva", "5");
		StudentRecord sr2 = new StudentRecord("0000000002", "Horvat", "Josip", "3");
		StudentRecord sr3 = new StudentRecord("0000000003", "Horvat", "Iva", "4");
		
		assertFalse(qf.accepts(sr1));
		assertFalse(qf.accepts(sr2));
		assertTrue(qf.accepts(sr3));
	}

}
