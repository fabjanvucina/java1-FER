package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node subclass which represents textual input.
 * 
 * @author fabjanvucina
 *
 */
public class TextNode extends Node {

	private String text;
	
	/**
	 * Public constructor.
	 * @param text
	 * @throws NullPointerException if <code>text == null</code>
	 */
	public TextNode(String text) {
		if(text == null) throw new NullPointerException("Text can't be null");
		
		this.text = text;
	}
	
	
	@Override
	public String getText() {
		return text;
	}
}
