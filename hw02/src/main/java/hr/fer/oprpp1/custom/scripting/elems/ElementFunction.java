package hr.fer.oprpp1.custom.scripting.elems;

/**
 * A class which represents a function expression.
 * 
 * @author fabjanvucina
 */
public class ElementFunction extends Element {
	
	/**
	 * A private variable which represents the name of the function expression.
	 */
	private String name;
	
	/**
	 * Public constructor.
	 * @param name
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String asText() {
		return '@' + this.name;
	}
}
