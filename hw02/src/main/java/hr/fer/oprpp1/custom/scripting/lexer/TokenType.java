package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * An enum which represents the types of a <code>Token</code> object.
 * 
 * @author fabjanvucina
 *
 */
public enum TokenType {
	
	/**
	 * End of file. No more tokens in the input document body.
	 */
	EOF,
	
	/**
	 * Token represents text.
	 */
	TEXT,
	
	/**
	 * Token represents a tag.
	 */
	TAG,
	
	/**
	 * Token represents a variable in a tag.
	 */
	VARIABLE,
	
	/**
	 * Token represents a constant integer in a tag.
	 */
	CONSTANT_INTEGER,
	
	/**
	 * Token represents a constant double in a tag.
	 */
	CONSTANT_DOUBLE,
	
	/**
	 * Token represents a string in a tag.
	 */
	STRING,
	
	/**
	 * Token represents a function in a tag.
	 */
	FUNCTION,
	
	/**
	 * Token represents an operator in a tag.
	 */
	OPERATOR
}	
