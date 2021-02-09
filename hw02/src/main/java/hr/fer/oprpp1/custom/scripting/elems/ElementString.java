package hr.fer.oprpp1.custom.scripting.elems;

/**
 * A class which represents a constant <code>String</code> expression.
 * 
 * @author fabjanvucina
 */
public class ElementString extends Element {
	
	/**
	 * A private variable which represents the value of the constant <code>String</code> expression.
	 */
	private String value;
	
	/**
	 * Public constructor.
	 * @param value
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	@Override
	public String asText() {
		return '\"' + this.value + '\"';
	}
}
