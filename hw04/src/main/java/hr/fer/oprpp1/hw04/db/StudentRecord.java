package hr.fer.oprpp1.hw04.db;

/**
 * A class which represents a record of a student in the database.
 * 
 * @author fabjanvucina
 */
public class StudentRecord {

	/**
	 * JMBAG of the student
	 */
	private String jmbag;
	
	/**
	 * last name of the student
	 */
	private String lastName;
	
	/**
	 * first name of the student
	 */
	private String firstName;
	
	/**
	 * final grade of the student
	 */
	private String finalGrade;
	
	/**
	 * Public constructor.
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	

	/**
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @return final grade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}	
}
