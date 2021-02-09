package hr.fer.oprpp1.custom.scripting.parser;

/**
 * An exception class which represents a type of <code>RuntimeException</code> thrown in a case of calling <code>pop()</code> on an empty stack.
 * 
 * @author fabjanvucina
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param message exception message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
}
