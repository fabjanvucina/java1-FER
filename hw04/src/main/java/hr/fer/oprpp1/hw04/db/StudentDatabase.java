package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Student database class.
 * 
 * @author fabjanvucina
 */
public class StudentDatabase {
	
	/**
	 * List of all students in the database
	 */
	private List<StudentRecord> students;
	
	/**
	 * A map of students which provides a data structure for a database index on the jmbag attribute
	 */
	private Map<String, StudentRecord> studentIndex;
	
	/**
	 * Public constructor.
	 * @param studentStrings
	 */
	public StudentDatabase(List<String> studentStrings) {
		
		students = new ArrayList<>();
		studentIndex = new HashMap<>();
		
		//parse every student string into StudentRecord
		for(String studentString : studentStrings) {
			
			Scanner sc = new Scanner(studentString);
			
			String jmbag = sc.next();
			if(studentIndex.get(jmbag) != null) {
				sc.close();
				throw new RuntimeException("There are duplicate students with jmbag = " + jmbag + " in the database!");
			}
			
			String lastName = sc.next();
			String firstName = sc.next();
			
			String finalGrade = sc.next();
			
			//student has 2 last names
			if(sc.hasNext()) {
				lastName = lastName + " " + firstName;
				firstName = finalGrade;
				finalGrade = sc.next();
			}
			
			if(Integer.parseInt(finalGrade) < 1 || Integer.parseInt(finalGrade) > 5) {
				sc.close();
				throw new RuntimeException(finalGrade + " is not a valid grade");
			}
						
			StudentRecord newStudent = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			students.add(newStudent);
			studentIndex.put(jmbag, newStudent);
			
			sc.close();
		}
	}
		
	/**
	 * A method which represents a jmbag databse index.
	 * @param jmbag
	 * @return student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentIndex.get(jmbag);
	}
	
	/**
	 * A method which filters the database using the specified <code>IFilter</code> object
	 * @param filter
	 * @return fitered students
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredStudents = new ArrayList<>();
		
		for(StudentRecord student : students) {
			if(filter.accepts(student)) {
				filteredStudents.add(student);
			}
		}
		
		return filteredStudents;
	}	
}
