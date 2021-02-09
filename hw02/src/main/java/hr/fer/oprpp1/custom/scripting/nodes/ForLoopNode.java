package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Node subclass which represents a for-loop.
 * 
 * @author fabjanvucina
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	/**
	 * Public constructor.
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 * @throws NullPointerException if any argument except <code>stepExpression</code> is <code>null</code>
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		if(variable == null) throw new NullPointerException("variable can't be null");
		if(startExpression == null) throw new NullPointerException("startExpression can't be null");
		if(endExpression == null) throw new NullPointerException("endExpression can't be null");
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	
	/**
	 * @return loop variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	
	/**
	 * @return loop start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	
	/**
	 * @return loop end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	
	/**
	 * @return loop step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}


	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$ FOR ");
		sb.append(variable.asText());
		sb.append(" ");
		sb.append(startExpression.asText());
		sb.append(" ");
		sb.append(endExpression.asText());
		sb.append(" ");
		if(stepExpression != null) {
			sb.append(stepExpression.asText());
			sb.append(" ");
		}
		sb.append("$}");
		
		return sb.toString();
	}
}
