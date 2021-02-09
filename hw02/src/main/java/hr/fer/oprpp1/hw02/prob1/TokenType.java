package hr.fer.oprpp1.hw02.prob1;

/**
 * An enum which represents the types of a <code>Token</code> object.
 * 
 * @author fabjanvucina
 */
public enum TokenType {
	
	/**
	 * End of file. No more tokens in the input text.
	 */
	EOF,
	
	/**
	 * Token is a word.
	 */
	WORD,
	
	/**
	 * Token is a number.
	 */
	NUMBER,
	
	/**
	 * Token is a symbol.
	 */
	SYMBOL
}