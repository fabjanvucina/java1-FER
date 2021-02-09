package hr.fer.oprpp1.hw04.db;

/**
 * A class which contains static implementations of field value getters for the database.
 * 
 * @author fabjanvucina
 */
public class FieldValueGetters {

	/**
	 * A field value getter which fetches the student's first name
	 */
	public static final IFieldValueGetter FIRST_NAME = student -> student.getFirstName();
	
	/**
	 * A field value getter which fetches the student's last name
	 */
	public static final IFieldValueGetter LAST_NAME = student -> student.getLastName();
	
	/**
	 * A field value getter which fetches the student's jmbag
	 */
	public static final IFieldValueGetter JMBAG = student -> student.getJmbag();
}
