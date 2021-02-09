package hr.fer.zemris.java.gui.calc;

import java.util.HashSet;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * An implementation of a simple calculator model.
 * 
 * @author fabjanvucina
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * Editability flag.
	 */
	private boolean isEditable;
	
	/**
	 * Input number sign flag.
	 */
	private boolean isNegative;
	
	/**
	 * String value of the current input number. It is either the string representation of <code>inputValue</code> or
	 * <code>frozenValue</code>.
	 */
	private String inputString;
	
	/**
	 * Primitive double value of the current input number.
	 */
	private double inputValue;
	
	/**
	 * The value of the first operand displayed in the calculator after the user selects a binary operator 
	 * and before the user starts inputing the second operand.
	 */
	private String frozenValue;
	
	/**
	 * Double value of the current active operand.
	 */
	private Double activeOperand;
	
	/**
	 * Pending operation to be performed on the activeOperand and new inputValue.
	 */
	private DoubleBinaryOperator pendingOperation;

	/**
	 * Set of listeners for this calculator model (observer design pattern).
	 */
	private Set<CalcValueListener> calcValueListeners;
	
	/**
	 * Public constructor.
	 */
	public CalcModelImpl() {
		isEditable = true;
		isNegative = false;
		inputString = "";
		inputValue = 0;
		frozenValue = null;
		activeOperand = null;
		pendingOperation = null;
		calcValueListeners = new HashSet<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l == null) throw new NullPointerException("You passed a null reference for l.");
		
		//add the listener
		calcValueListeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(l == null) throw new NullPointerException("You passed a null reference for l.");
		
		//remove the listener
		calcValueListeners.remove(l);
	}
	
	/**
	 * Private method which notifies listeners interested in this model of a change whenever there is one.
	 */
	private void notifyListeners() {
		for(CalcValueListener l : calcValueListeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return inputValue;
	}

	/**
	 * This method is called upon pressing =, a finish of a unary operation or popping from stack.
	 * After this method gets called, the model becomes uneditable. 
	 * Next operation can not be an insertion of a digit, insertion of a decimal point or a sign swap.
	 * This method should reset the frozen value if it exists.
	 */
	@Override
	public void setValue(double value) {
		if(hasFrozenValue()) {
			frozenValue = null;
		}
		
		if(value < 0) isNegative = true;
		inputValue = value;
		inputString = String.valueOf(inputValue);
		
		//specified in task
		isEditable = false;
		
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		inputString = "";
		inputValue = 0;
		isNegative = false;
		
		isEditable = true;
		
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		setPendingBinaryOperation(null);
		
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) throw new CalculatorInputException("Can't swap sign because model is not editable.");
		
		//swap sign
		if(inputString.equals("")) {
			//do nothing
		}
		else if(isNegative) {
			inputString = inputString.substring(1);
		}
		else {
			inputString = "-".concat(inputString);
		}
		
		isNegative = !isNegative;
		inputValue *= -1.0;
		
		//reset frozen value because displayed value has changed
		frozenValue = null;
		
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) throw new CalculatorInputException("Can't swap sign because model is not editable.");
		if(inputString.contains(".")) throw new CalculatorInputException("Input already contains decimal point.");
		if(inputString.equals("")) throw new CalculatorInputException("Input doesn't contain any digits. Can't add decimal point.");
		
		//insert decimal point
		inputString = inputString.concat(".");
		inputValue =  Double.parseDouble(inputString);
		
		//reset frozen value because displayed value has changed
		frozenValue = null;
		
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable) throw new CalculatorInputException("Can't insert digit because model is not editable.");
		if(digit < 0 || digit > 9) throw new IllegalArgumentException("Digit has to be in [0, 9] range.");
	
		//check if parseable with new digit
		String newStringValue = inputString.concat(String.valueOf(digit));
		try {
			inputValue = Double.parseDouble(newStringValue);
			
			if(inputValue == Double.POSITIVE_INFINITY || inputValue == Double.NEGATIVE_INFINITY) {
				throw new CalculatorInputException("Number is too big.");
			}
		}
		
		catch(NumberFormatException ex) {
			throw new CalculatorInputException(newStringValue + " is not parseable into a double.");
		}
		
		
		//e.g. prevent 034
		//if new digit is not 0 and current value is 0
		if(!(digit == 0) && (inputString.equals("0") || inputString.equals("-0"))) {
			inputString = isNegative ? "-".concat(String.valueOf(digit)) : String.valueOf(digit);
		}
		
		//Concatenate new digit to the end the current inputString, unless duplicate zero
		else if(!(digit == 0) || (digit == 0 && !inputString.equals("0") && !inputString.equals("-0"))) {
			inputString = newStringValue;
		}
		
		//reset frozen value because displayed value has changed
		frozenValue = null;
		
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand == null ? false : true;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) throw new IllegalStateException("There is no active operand.");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		
		notifyListeners();
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
		
		notifyListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {		
		pendingOperation = op;
		
		notifyListeners();
	}

	@Override
	public String toString() {
		if(hasFrozenValue()) {
			return frozenValue;
		}
		
		else if(inputString.equals("")) {
			return isNegative ? "-0" : "0";
		}
		
		else {
			return inputString;
		}
	}

	@Override
	public void freezeValue(String value) {
		if(value == null) throw new NullPointerException("You passed a null reference for value.");
		
		frozenValue = value;
		
		notifyListeners();
	}

	@Override
	public boolean hasFrozenValue() {
		if(frozenValue == null) {
			return false;
		}
		
		return true;
	}	
}
