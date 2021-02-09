package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertTrue; 
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	@Test
	public void testFunctionality() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE
				);
		
		StudentRecord student1 = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		boolean student1Satisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(student1), 
				expr.getStringLiteral()
				);
		assertTrue(student1Satisfies);
		
		StudentRecord student2 = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		boolean student2Satisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(student2), 
				expr.getStringLiteral()
				);
		assertFalse(student2Satisfies);
	}
}
