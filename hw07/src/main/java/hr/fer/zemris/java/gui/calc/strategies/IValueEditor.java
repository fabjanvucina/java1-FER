package hr.fer.zemris.java.gui.calc.strategies;

/**
 * A functional interface which strategizes inserting digits, decimal points or swapping signs.
 * 
 * @author fabjanvucina
 */
public interface IValueEditor {
	
	/**
	 * A method which edits the current calculator value.
	 */
	void editValue();
}
