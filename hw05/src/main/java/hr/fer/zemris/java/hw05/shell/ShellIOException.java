package hr.fer.zemris.java.hw05.shell;

/**
 * An exception class which represents a type of <code>RuntimeException</code> thrown in a case of an exception which occured 
 * during the runtime of a <code>MyShell</code> object.
 * 
 * @author fabjanvucina
 */
public class ShellIOException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param message exception message
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
