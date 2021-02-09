package hr.fer.oprpp1.hw04.db;

/**
 * A class which keeps the static implementations of various comparison operators.
 * 
 * @author fabjanvucina
 */
public class ComparisonOperators {
	
	/**
	 * A comparison operator which returns <code>true</code> if value1 is smaller than value2
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;
	
	/**
	 * A comparison operator which returns <code>true</code> if value1 is smaller than or equal to value2
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) < 0 || s1.compareTo(s2) == 0;
	
	/**
	 * A comparison operator which returns <code>true</code> if value1 is bigger than value2
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
	
	/**
	 * A comparison operator which returns <code>true</code> if value1 is bigger than or equal to value2
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) > 0 || s1.compareTo(s2) == 0;
	
	/**
	 * A comparison operator which returns <code>true</code> if value1 is equal to value2
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;
	
	/**
	 * A comparison operator which returns <code>true</code> if value1 is not equal to value2
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;
	
	/**
	 * A comparison operator which returns <code>true</code> if value1 is satifies the value2 pattern
	 */
	public static final IComparisonOperator LIKE = (s1, s2) -> {
		
		boolean result = false;
		int wildcardCount = countOccurencesOfSymbol("*", s2);
		
		//if there is more than one * wildcard
		if(wildcardCount > 1) {
			throw new IllegalArgumentException("There can be maximum of 1 * wildcard in the LIKE pattern");
		}
		
		//no wildcard, s1 has to be equal to the pattern s2
	    if(countOccurencesOfSymbol("*", s2) == 0) {
			 result = s1.compareTo(s2) == 0;
		}
		
		//1 wildcard
		if(countOccurencesOfSymbol("*", s2) == 1) {
			String[] patternParts = s2.split("\\*");
			
			//only suffix
			if(s2.indexOf("*") == 0) {
				String suffix = patternParts[1];
				result = s1.endsWith(suffix);
			}
			
			//only prefix
			else if(s2.indexOf("*") == s2.length() - 1) {
				String prefix = patternParts[0];
				result = s1.startsWith(prefix);
			}
			
			//prefix and suffix
			else {
				String prefix = patternParts[0];
				boolean hasPrefix = s1.startsWith(prefix);
				
				String suffix = patternParts[1];
				String s1WithoutPrefix = s1.substring(prefix.length());
				boolean hasSuffix = s1WithoutPrefix.endsWith(suffix);
				
				result = hasPrefix && hasSuffix;
			}
		}
		
		return result;
	};
	
	/**
	 * @param symbol
	 * @param string
	 * @return number of occurences of symbol in string
	 */
	private static int countOccurencesOfSymbol(String symbol, String string) {
		return string.length() - string.replace("*", "").length();
	}
}
