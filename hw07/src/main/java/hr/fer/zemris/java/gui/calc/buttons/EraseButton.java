package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color; 

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.strategies.IEraser;

/**
 * A Swing button component which has a functionality of clearing the value or reseting the calculator.
 * 
 * @author fabjanvucina
 */
public class EraseButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param buttonText
	 * @param eraser
	 */
	public EraseButton(String buttonText, IEraser eraser) {
		super(buttonText);
		
		setFont(getFont().deriveFont(20f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		addActionListener(e -> {
			eraser.erase();
		});
	}
}
