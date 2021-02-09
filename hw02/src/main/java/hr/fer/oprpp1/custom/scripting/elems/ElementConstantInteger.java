package hr.fer.oprpp1.custom.scripting.elems;

/**
 * A class which represents a constant <code>Integer</code> expression.
 * 
 * @author fabjanvucina
 */
public class ElementConstantInteger extends Element {
	
	/**
	 * A private variable which represents the value of the constant <code>Integer</code> expression.
	 */
	private int value;
	
	/**
	 * Public constructor.
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
}
