package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which provides formatting for query output.
 * 
 * @author fabjanvucina
 */
public class RecordFormatter {
	
	/**
	 * A method which formats the query input.
	 * @param records
	 * @return formatted output
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> outputLines = new ArrayList<>();
		
		//variables to remember max attribute lengths
		int maxJmbagLength = 0;
		int maxLastNameLength = 0;
		int maxFirstNameLength = 0;
		
		if(records.size() == 1 && records.get(0) == null) {
			return null;
		}
		
		else {
			
			//first iteration to determine max attribute lengths
			for(StudentRecord record : records) {
				if(record.getJmbag().length() > maxJmbagLength) {
					maxJmbagLength = record.getJmbag().length();
				}
				
				if(record.getLastName().length() > maxLastNameLength) {
					maxLastNameLength = record.getLastName().length();
				}
				
				if(record.getFirstName().length() > maxFirstNameLength) {
					maxFirstNameLength = record.getFirstName().length();
				}
			}
			
			//second iteration for output
			for(StudentRecord record : records) {
				StringBuilder sb = new StringBuilder();
				
				//jmbag
				appendAttribute(sb, record.getJmbag());
				addWhitespaces(sb, maxJmbagLength, record.getJmbag().length());
				
				//last name
				appendAttribute(sb, record.getLastName());
				addWhitespaces(sb, maxLastNameLength, record.getLastName().length());
				
				//first name
				appendAttribute(sb, record.getFirstName());
				addWhitespaces(sb, maxFirstNameLength, record.getFirstName().length());
				
				//grade
				appendAttribute(sb, record.getFinalGrade());
				sb.append(" |");
				
				//add line to list
				outputLines.add(sb.toString());
			}
			
			//create output borders
			StringBuilder sb = new StringBuilder();
			
			sb.append("+");
			appendEqualSymbols(sb, maxJmbagLength);
			sb.append("+");
			appendEqualSymbols(sb, maxLastNameLength);
			sb.append("+");
			appendEqualSymbols(sb, maxFirstNameLength);
			sb.append("+");
			appendEqualSymbols(sb, 1);
			sb.append("+");
			
			//add to output lines
			outputLines.add(0, sb.toString());
			outputLines.add(sb.toString());
			
			return outputLines;
		}

	}
	
	/**
	 * Private method which appends an attribute to the output line
	 * @param sb <code>StringBuilder</code> object
	 * @param attribute
	 */
	private static void appendAttribute(StringBuilder sb, String attribute) {
		sb.append("| ");
		sb.append(attribute);
	}
	
	/**
	 * A method which adds whitespaces after an attribute in an output line
	 * @param sb <code>StringBuilder</code> object
	 * @param maxLength character width that needs to be reached with whitespaces
	 * @param length starting character width to which we are adding whitespaces to reach <code>maxLength</code>
	 */
	private static void addWhitespaces(StringBuilder sb, int maxLength, int length) {
		for(int i=0, n = maxLength - length + 1; i<n; i++, sb.append(" "));
	}
	
	/**
	 * A method which appends = symbols for the output borders
	 * @param sb <code>StringBuilder</code> object
	 * @param maxLength number of = symbols to be appended
	 */
	private static void appendEqualSymbols(StringBuilder sb, int maxLength) {
		for(int i=0; i<maxLength+2; i++, sb.append("="));
	}
}
