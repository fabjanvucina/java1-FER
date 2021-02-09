package hr.fer.oprpp1.custom.scripting.parser;

/**
 * An exception class which represents a type of <code>RuntimeException</code> thrown in a case of an exception which occured during the process of script parsing.
 * 
 * @author fabjanvucina
 */
public class SmartScriptParserException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param message exception message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}