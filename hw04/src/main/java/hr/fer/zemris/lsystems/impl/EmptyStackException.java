package hr.fer.zemris.lsystems.impl;

/**
 * A class which represents a type of <code>RuntimeException</code> thrown in a case of calling <code>pop()</code> on an empty stack.
 * 
 * @author fabjanvucina
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor which passes an exception message.
	 * 
	 * @param message exception message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
}
