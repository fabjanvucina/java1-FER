package hr.fer.oprpp1.hw04.db;

/**
 * A functional interface which represents a contract fot behavior of comparison operators in the database.
 * 
 * @author fabjanvucina
 */
public interface IComparisonOperator {
	
	/**
	 * @param value1
	 * @param value2
	 * @return <code>true</code> if the relation of passed values satisfies the comparison implementatio, <code>false</code> otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
