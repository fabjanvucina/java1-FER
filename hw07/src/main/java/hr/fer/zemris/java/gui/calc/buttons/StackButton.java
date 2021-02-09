package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.util.EmptyStackException;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.strategies.IStackManipulator;

/**
 * A Swing button component which has a functionality of a manipulating the operand memory stack.
 * 
 * @author fabjanvucina
 */
public class StackButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param buttonText
	 * @param manipulator
	 * @param calc
	 */
	public StackButton(String buttonText, IStackManipulator manipulator, Calculator calc) {
		super(buttonText);
		
		setFont(getFont().deriveFont(20f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		addActionListener(e -> {
			try {
				manipulator.manipulate();
			}
			catch (EmptyStackException ex) {
				calc.getModel().clearAll();
				calc.getDisplayLabel().setText("err");
				System.out.println("The stack is empty");
			}
		});
	}
}
