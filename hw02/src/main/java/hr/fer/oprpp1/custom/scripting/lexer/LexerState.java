package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * An enum which represents the modes of operation for a <code>SmartScriptLexer</code> object.
 * 
 * @author fabjanvucina
 */
public enum LexerState {
	
	/**
	 * Mode of operation when getting TEXT token from document.
	 */
	TEXT,
	
	/**
	 * Mode of operation when getting TAG token from document.
	 */
	TAG,
	
	/**
	 * Mode of operation when tokenizing TAG into elements.
	 */
	ELEMENTS
}
