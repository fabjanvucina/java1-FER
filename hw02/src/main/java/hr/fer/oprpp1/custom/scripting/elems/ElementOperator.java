package hr.fer.oprpp1.custom.scripting.elems;

/**
 * A class which represents an operator expression.
 * 
 * @author fabjanvucina
 */
public class ElementOperator extends Element {

	/**
	 * A private variable which represents the symbol of the operator expression.
	 */
	private String symbol;
	
	/**
	 * Public constructor.
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return this.symbol;
	}
}
