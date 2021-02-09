package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * A Swing button component which has a functionality of a binary arithmetic operation.
 * 
 * @author fabjanvucina
 */
public class BinaryOperationButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor for multiplication, division, subtraction and addition.
	 * @param buttonText
	 * @param operation
	 * @param model
	 */
	public BinaryOperationButton(String buttonText, DoubleBinaryOperator operation, CalcModel model) {
		
		super(buttonText);
		
		setFont(getFont().deriveFont(15f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		addActionListener(e -> {
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation(operation);
			
			//freeze first operand in the display
			model.freezeValue(model.toString());
			
			//.... but clear the calculator value for second operand
			model.clear();
		});
	}

	/**
	 * Public constructor for the power operation.
	 * @param buttonText
	 * @param operation
	 * @param inversedOperation
	 * @param calc
	 */
	public BinaryOperationButton(String buttonText, DoubleBinaryOperator operation, 
								 DoubleBinaryOperator inversedOperation, Calculator calc) {
		
		super(buttonText);
		
		setFont(getFont().deriveFont(15f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		CalcModel model = calc.getModel();
		
		addActionListener(e -> {
			model.setActiveOperand(model.getValue());
			
			if(calc.isInversed()) 
				model.setPendingBinaryOperation(inversedOperation);	
			else 
				model.setPendingBinaryOperation(operation);
			
			//freeze first operand in the display
			model.freezeValue(model.toString());
			
			//.... but clear the calculator value for second operand
			model.clear();
		});
	}	
}
