package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color; 

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * A Swing checkbox component which inverts the invertable operations.
 * 
 * @author fabjanvucina
 */
public class InvertButton extends JCheckBox {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Public constructor.
	 * @param calc
	 */
	public InvertButton(Calculator calc) {
		super(String.valueOf("Inv"));
		
		setFont(getFont().deriveFont(20f));
		setBackground(Color.ORANGE);
		setOpaque(true);
		
		addActionListener(e -> {
			calc.invert();
		});
	}	
}
