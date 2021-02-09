package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * A Swing button component which has a functionality of a unary arithmetic operation.
 * 
 * @author fabjanvucina
 */
public class UnaryOperationButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor.
	 * @param buttonText
	 * @param operation
	 * @param inversedOperation
	 * @param calc
	 */
	public UnaryOperationButton(String buttonText,
								DoubleUnaryOperator operation, 
								DoubleUnaryOperator inversedOperation, 
								Calculator calc) {
		
		super(buttonText);
		
		setFont(getFont().deriveFont(15f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		CalcModel model = calc.getModel();
		
		addActionListener(e -> {
			if(calc.isInversed()) {
				model.setValue(inversedOperation.applyAsDouble(model.getValue()));
			}
			else {
				model.setValue(operation.applyAsDouble(model.getValue()));
			}
		});
	}
}
