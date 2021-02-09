package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * A class which represents a filter comprised of conditonal expressions.
 * 
 * @author fabjanvucina
 */
public class QueryFilter implements IFilter {
	
	/**
	 * List of conditional expression in a query
	 */
	private List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Public constructor.
	 * @param conditionalExpressions
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
		this.conditionalExpressions = conditionalExpressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		
		boolean accepted = true;
		
		//run record through every conditional expression
		for(var conditionalExpression : conditionalExpressions) {
			try {
				
				//our record is accepted only if it passes all expressions
				if(!accepted) {
					break;
				}
				
				//check if record satifies expression
				accepted = conditionalExpression.getComparisonOperator()
				 					 			.satisfied(conditionalExpression.getFieldGetter().get(record), conditionalExpression.getStringLiteral());
				
			}
			catch(IllegalStateException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return accepted;
	}
}
