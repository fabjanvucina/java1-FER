package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Parser class which parses a query into a list of conditional expressions.
 * 
 * @author fabjanvucina
 */
public class QueryParser {
	
	/**
	 * The query that is getting parsed
	 */
	private String query;
	
	/**
	 * Public constructor.
	 * @param query
	 */
	public QueryParser(String query) {
		this.query = query;
	}
	
	/**
	 * @return <code>true</code> if the query is a direct query, <code>false></code> otherwise
	 */
	public boolean isDirectQuery() {
		Scanner sc = new Scanner(query);
		sc.useDelimiter("<|>|!|=|LIKE");
		
		//a query isn't direct if it includes a comparison expression for an attribute that isn't jmbag
		String fieldName = sc.next();
		if(!fieldName.trim().equals("jmbag")) {
			sc.close();
			return false;
		}
		
		//if operator isn't =, the query isn't direct
		sc.useDelimiter("\"");
		String operator = sc.next();
		if(!operator.trim().equals("=")) {
			sc.close();
			return false;
		}
		
		//get to the enclosing quote marks of literal
		sc.next();
		
		//if there is anything after the enclosing quote marks -> query not direct
		if(sc.hasNext() && !sc.next().trim().equals("")) {
			sc.close();
			return false;
		}
		
		sc.close();
		
		//query is direct
		return true;
	}
	
	/**
	 * @return the comparison value for the jmbag attribute
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException("Can't get queried jmbag if query wasn't direct.");
		
		return extractValueFromLiteral(query);
	}
	
	/**
	 * A private method which extracts the string value from the string literal in a query.
	 * @param query
	 * @return literal value
	 */
	private String extractValueFromLiteral(String query) {
		//create scanner for query
		Scanner sc = new Scanner(query);
		
		//read everything until you encounter = (it will happen because this query is a direct one)
		sc.useDelimiter("=");
		//skip the field name
		sc.next();
		
		//read everything until you encounter \" (start of comparison value)
		sc.useDelimiter("\"");
		//skip to \"
		sc.next();
		
		//the next thing the scanner will read is the value inside the literal
		String value = sc.next();
		sc.close();
		
		return value;
	}
	
	/**
	 * A method which parses a query into separate conditional expressions.
	 * @return list of conditional expressions in the query
	 */
	public List<ConditionalExpression> getQuery() {
		
		List<ConditionalExpression> conditionalExpressions = new ArrayList<>();
		
		//split query into expressions and parse every expresson into a ConditionalExpression object
		String[] expressions = query.split("and|anD|aNd|aND|And|ANd|AND");
		for(String expression : expressions) {
			Scanner sc = new Scanner(expression);
			
			//read field name
			sc.useDelimiter("<|>|!|=|LIKE");
			String fieldName = sc.next();
			
			IFieldValueGetter fieldGetter;
			switch(fieldName.trim().toLowerCase()) {
			 	case "firstname": 
			 		fieldGetter = FieldValueGetters.FIRST_NAME;
	                break; 
	                
	            case "lastname": 
	            	fieldGetter = FieldValueGetters.LAST_NAME;
	                break; 
	            
	            case "jmbag": 
	            	fieldGetter = FieldValueGetters.JMBAG;
	                break; 
	                
	            default: 
	            	sc.close();
	                throw new IllegalArgumentException(fieldName.trim() + " is an invalid field name");
			}
			
			//read operator
			sc.useDelimiter("\"");
			String operator = sc.next();
			
			IComparisonOperator comparisonOperator;
			switch(operator.trim()) {
		 		case "<": 
		 			comparisonOperator = ComparisonOperators.LESS;
		 			break; 
                
		 		case "<=": 
		 			comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
		 			break; 
            
		 		case ">": 
		 			comparisonOperator = ComparisonOperators.GREATER;
		 			break; 
		 			
		 		case ">=": 
		 			comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
		 			break; 
                
		 		case "=": 
		 			comparisonOperator = ComparisonOperators.EQUALS;
		 			break; 
            
		 		case "!=": 
		 			comparisonOperator = ComparisonOperators.NOT_EQUALS;
		 			break; 
		 			
		 		case "LIKE": 
		 			comparisonOperator = ComparisonOperators.LIKE;
		 			break; 
                
		 		default: 
		 			sc.close();
		 			throw new IllegalArgumentException(operator.trim() + " is an invalid comparison operator");				
			}
			
			//read literal value
			String stringLiteral = sc.next();
			sc.close();
			
			//create new ConditionalExpression object and add it to the list
			conditionalExpressions.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
		}
		
		return conditionalExpressions;
	}
}
