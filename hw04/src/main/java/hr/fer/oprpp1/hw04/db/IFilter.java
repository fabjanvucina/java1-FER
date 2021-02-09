package hr.fer.oprpp1.hw04.db;

/**
 * A functional interface which represents a contract for behavior of appliable filters.
 * 
 * @author fabjanvucina
 */
public interface IFilter {
	
	/**
	 * @param record
	 * @return <code>true</code> if the record satisifies the condition, <code>false</code> otherwise
	 */
	public boolean accepts(StudentRecord record);
}
