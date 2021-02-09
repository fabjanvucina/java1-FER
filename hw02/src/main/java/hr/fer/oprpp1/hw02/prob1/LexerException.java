package hr.fer.oprpp1.hw02.prob1;

/**
 * An exception class which represents a type of <code>RuntimeException</code> thrown in a case of an exception which occured during the process of lexical analysis.
 * 
 * @author fabjanvucina
 */
public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param message exception message
	 */
	public LexerException(String message) {
		super(message);
	}
}
