package hr.fer.oprpp1.hw02.prob1;

/**
 * A token class which represents a lexical unit in the lexical analysis procedure. A token is generated grouping 1 or more characters following lexical rules. 
 * 
 * @author fabjanvucina
 */
public class Token {
	
	/**
	 * Type of the <code>Token</code> object.
	 */
	private TokenType type;
	
	/**
	 * Value of the <code>Token</code> object.
	 */
	private Object value;
	
	/**
	 * Public constructor.
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	
	/**
	 * @return value
	 */
	public Object getValue() {
		return this.value;
	}
	
	
	/**
	 * @return type
	 */
	public TokenType getType() {
		return this.type;
	}
}