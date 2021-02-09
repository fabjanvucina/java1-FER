package hr.fer.oprpp1.custom.scripting.elems;

/**
 * A class which represents a variable expression.
 * 
 * @author fabjanvucina
 */
public class ElementVariable extends Element {
	
	/**
	 * A private variable which represents the name of the variable expression.
	 */
	private String name;
	
	/**
	 * Public constructor.
	 * @param nam
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}
}
