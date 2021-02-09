package hr.fer.oprpp1.custom.scripting.elems;

/**
 * A class which represents an expression inside a for loop or an echo tag.
 * 
 * @author fabjanvucina
 */
public abstract class Element {
	
	/**
	 * @return textual representation
	 */
	public abstract String asText();
}
