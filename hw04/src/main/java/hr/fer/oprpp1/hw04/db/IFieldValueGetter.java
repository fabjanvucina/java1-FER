package hr.fer.oprpp1.hw04.db;

/**
 * A functional interface which represents a contract of behavior for getters of record field values
 * 
 * @author fabjanvucina
 */
public interface IFieldValueGetter {

	/**
	 * @param record
	 * @return the field value of the record
	 */
	public String get(StudentRecord record);
}
