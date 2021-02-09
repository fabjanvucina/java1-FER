package hr.fer.zemris.java.gui.layouts;

/**
 * An exception class which represents a type of <code>RuntimeException</code> thrown in a case of an exception 
 * which occures if a user adds components in a way that isn't compliant with the <code>CalcLayout</code> layout manager.
 * 
 * @author fabjanvucina
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor for the exception.
	 * 
	 * @param message exception message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}	
}
