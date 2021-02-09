package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Node subclass which represents dynamically generated output.
 * @author fabjanvucina
 *
 */
public class EchoNode extends Node {
	
	/**
	 * Elements of the echo node.
	 */
	private Element[] elements;
	
	/**
	 * Public constructor.
	 * @param elements
	 * @throws NullPointerException if <code>elements == null</code>
	 */
	public EchoNode(Element[] elements) {
		if(elements == null) throw new NullPointerException("elements can't be null");
		
		this.elements = elements;
	}

	
	/**
	 * @return elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	
	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$ = ");
		for(Element e : elements) {
			sb.append(e.asText());
			sb.append(" ");
		}
		sb.append("$}");
		
		return sb.toString();
	}
}
