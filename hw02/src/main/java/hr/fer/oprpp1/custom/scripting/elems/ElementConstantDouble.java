package hr.fer.oprpp1.custom.scripting.elems;

/**
 * A class which represents a constant double expression.
 * 
 * @author fabjanvucina
 */
public class ElementConstantDouble extends Element {

	/**
	 * A private variable which represents the value of the constant <code>Double</code> expression.
	 */
	private double value;
	
	/**
	 * Public constructor.
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Double.toString(this.value);
	}
}
