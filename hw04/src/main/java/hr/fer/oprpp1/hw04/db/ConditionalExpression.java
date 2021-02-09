package hr.fer.oprpp1.hw04.db;

/**
 * A class which represents a conditional expression which can occur in a query
 * 
 * @author fabjanvucina
 */
public class ConditionalExpression {

	/**
	 * Record attribute
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * Comparison value
	 */
	private String stringLiteral;
	
	/**
	 * Comparison operator
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Public constructor.
	 * @param fieldGetter
	 * @param stringLiteral
	 * @param comparisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * @return record attribute
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * @return comparison value
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
