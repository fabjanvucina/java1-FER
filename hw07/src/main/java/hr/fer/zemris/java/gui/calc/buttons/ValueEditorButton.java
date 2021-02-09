package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;  

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.calc.strategies.IValueEditor;

/**
 * A Swing button component has a functionality of a editing the current value of the calculator.
 * 
 * @author fabjanvucina
 */
public class ValueEditorButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor for digit buttons.
	 * @param digit
	 * @param calc
	 */
	public ValueEditorButton(int digit, Calculator calc) {
		super(String.valueOf(digit));
		
		setFont(getFont().deriveFont(30f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		addActionListener(e -> {
			try {
				calc.getModel().insertDigit(digit);
			}
			catch (CalculatorInputException ex) {
				calc.getModel().clearAll();
				calc.getDisplayLabel().setText("err");
				System.out.println("The calculator is uneditable at this moment.");
			}
		});
	}
	
	/**
	 * Public constructor for the insert decimal point and swap sign buttons.
	 * @param text
	 * @param editor
	 * @param calc
	 */
	public ValueEditorButton(String text, IValueEditor editor, Calculator calc) {
		super(text);
		
		setFont(getFont().deriveFont(30f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		addActionListener(e -> {
			try {
				editor.editValue();
			}
			catch (CalculatorInputException ex) {
				calc.getModel().clearAll();
				calc.getDisplayLabel().setText("err");
				System.out.println("The calculator is uneditable at this moment.");
			}
		});
	}
}
