package hr.fer.zemris.java.gui.calc;

import java.awt.Color; 
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.EraseButton;
import hr.fer.zemris.java.gui.calc.buttons.InvertButton;
import hr.fer.zemris.java.gui.calc.buttons.ValueEditorButton;
import hr.fer.zemris.java.gui.calc.buttons.StackButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * A calculator created with Swing.
 * 
 * @author fabjanvucina
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model for the program.
	 */
	private CalcModel model;
	
	/**
	 * Memory stack for saved operands.
	 */
	private Stack<Double> stack;
	
	/**
	 * Flag which stores the state of the invertible calculator operations.
	 */
	private boolean isInversed;
	
	/**
	 * Calculator display.
	 */
	private JLabel displayLabel;
	
	/**
	 * Helper map which stores display names for regular operations.
	 */
	private Map<JButton, String> buttonsNames;

	/**
	 * Helper map which stores display names for inversed operations.
	 */
	private Map<JButton, String> invertedButtonsNames;

	/**
	 * Public constructor.
	 */
	public Calculator() {
		model = new CalcModelImpl();
		stack = new Stack<>();
		isInversed = false;
		buttonsNames = new HashMap<>();
		invertedButtonsNames = new HashMap<>();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		initGUI();
		pack();
		setLocationRelativeTo(null);
	}
	
	/**
	 * A private method which initializes the components inside the calculator frame.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(4));
		
		addDisplayLabel(cp);
		
		addEqualityButton(cp);
		
		addNumberButtons(cp);
		
		addSpecialButtons(cp);
		
		addUnaryOperationButtons(cp);
		
		addBinaryOperationButtons(cp);
	}
	
	/**
	 * A method which adds the display label for the calculator user interface.
	 * @param cp top-level component
	 */
	private void addDisplayLabel(Container cp) {
	    displayLabel = new JLabel("0", SwingConstants.RIGHT);
	    displayLabel.setFont(displayLabel.getFont().deriveFont(50f));
	    displayLabel.setBackground(Color.YELLOW);
	    displayLabel.setOpaque(true);
		model.addCalcValueListener(mod -> displayLabel.setText(mod.toString()));
		
		cp.add(displayLabel, new RCPosition(1,1));
	}
	
	/**
	 * A method which adds the <code>=</code> button.
	 * @param cp top-level component
	 */
	private void addEqualityButton(Container cp) {
		JButton b = new JButton("=");
		b.setFont(b.getFont().deriveFont(20f));
		b.setBackground(Color.ORANGE);
		b.setOpaque(true);
		
		//calculate binary operation result
		b.addActionListener(e -> {
			try {
				double result = model.getPendingBinaryOperation()
									 .applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setValue(result);
				model.clearActiveOperand();
				model.setPendingBinaryOperation(null);
			}
			catch (IllegalStateException ex) {
				model.clearAll();
				displayLabel.setText("err");
				System.out.println("There is no active operand");
			}
		});
		
		cp.add(b, new RCPosition(1,6));
	}
	
	/**
	 * A method which adds the digit buttons, decimal point button and swap sign button.
	 * @param cp top-level component.
	 */
	private void addNumberButtons(Container cp) {
		cp.add(new ValueEditorButton(0, this), new RCPosition(5,3));
		cp.add(new ValueEditorButton(1, this), new RCPosition(4,3));
		cp.add(new ValueEditorButton(2, this), new RCPosition(4,4));
		cp.add(new ValueEditorButton(3, this), new RCPosition(4,5));
		cp.add(new ValueEditorButton(4, this), new RCPosition(3,3));
		cp.add(new ValueEditorButton(5, this), new RCPosition(3,4));
		cp.add(new ValueEditorButton(6, this), new RCPosition(3,5));
		cp.add(new ValueEditorButton(7, this), new RCPosition(2,3));
		cp.add(new ValueEditorButton(8, this), new RCPosition(2,4));
		cp.add(new ValueEditorButton(9, this), new RCPosition(2,5));
		
		cp.add(new ValueEditorButton("+/-", () -> model.swapSign(), this), new RCPosition(5,4));
		cp.add(new ValueEditorButton(".", () -> model.insertDecimalPoint(), this), new RCPosition(5,5));
	}
	
	/**
	 * A method which adds buttons with functionalities such as: 
	 * <code>clr</code>, <code>res</code>, <code>push</code>, <code>pop</code> and <code>Inv</code>.
	 * @param cp top-level component
	 */
	private void addSpecialButtons(Container cp) {
		cp.add(new EraseButton("clr", () -> model.clear()), new RCPosition(1, 7));
		cp.add(new EraseButton("res", () -> {model.clearAll(); stack.empty();}), new RCPosition(2, 7));
		cp.add(new StackButton("push", () -> stack.push(model.getValue()), this), new RCPosition(3, 7));
		cp.add(new StackButton("pop", () -> model.setValue(stack.pop()), this), new RCPosition(4, 7));
		cp.add(new InvertButton(this), new RCPosition(5,7));
	}
	
	/**
	 * A method which inverts the invertible calculator operations.
	 */
	public void invert() {
		isInversed = !isInversed;
		
		if(isInversed) {
			for(var entry : invertedButtonsNames.entrySet()) {
				entry.getKey().setText(entry.getValue());
			}
		}
		
		else {
			for(var entry : buttonsNames.entrySet()) {
				entry.getKey().setText(entry.getValue());
			}
		}
	}
	
	/**
	 * A method which adds <code>1/x</code>, <code>x^n</code>, <code>log</code>, <code>ln</code>, <code>sin</code>, 
	 * <code>cos</code>, <code>tan</code>, <code>ctg</code> or their inverses depending on the inverse checkbox.
	 * @param cp top level component
	 */
	private void addUnaryOperationButtons(Container cp) {
		var reciprocal = new UnaryOperationButton("1/x", x -> 1/x, x -> 1/x, this); 
		buttonsNames.put(reciprocal, "1/x");
		invertedButtonsNames.put(reciprocal, "1/x");
		cp.add(reciprocal, new RCPosition(2,1));
		
		
		var sin = new UnaryOperationButton("sin", Math::sin, Math::asin, this);
		buttonsNames.put(sin, "sin");
		invertedButtonsNames.put(sin, "arcsin");
		cp.add(sin,new RCPosition(2,2));
		
		var log = new UnaryOperationButton("log", Math::log10, x -> Math.pow(10, x), this);
		buttonsNames.put(log, "log");
		invertedButtonsNames.put(log, "10^x");
		cp.add(log, new RCPosition(3,1));
		
		var cos = new UnaryOperationButton("cos", Math::cos, Math::acos, this);
		buttonsNames.put(cos, "cos");
		invertedButtonsNames.put(cos, "arccos");
		cp.add(cos, new RCPosition(3,2));
		
		var ln = new UnaryOperationButton("ln", Math::log, x -> Math.pow(Math.E, x), this);
		buttonsNames.put(ln, "ln");
		invertedButtonsNames.put(ln, "e^x");
		cp.add(ln, new RCPosition(4,1));
		
		var tan = new UnaryOperationButton("tan", Math::tan, Math::atan, this);
		buttonsNames.put(tan, "tan");
		invertedButtonsNames.put(tan, "arctan");
		cp.add(tan, new RCPosition(4,2));
		
		var ctg = new UnaryOperationButton("ctg", x -> 1 / Math.tan(x), x -> (Math.PI / 2) - Math.atan(x), this);
		buttonsNames.put(ctg, "ctg");
		invertedButtonsNames.put(ctg, "arcctg");
		cp.add(ctg, new RCPosition(5,2));
	}
	
	/**
	 * A method which adds binary operations: <code>+</code>, <code>-</code>, <code>*</code>, <code>/</code>.
	 * @param cp top-level component
	 */
	private void addBinaryOperationButtons(Container cp) {
		cp.add(new BinaryOperationButton("/", (x,y) -> x/y, model), new RCPosition(2, 6));
		cp.add(new BinaryOperationButton("*", (x,y) -> x*y, model), new RCPosition(3, 6));
		cp.add(new BinaryOperationButton("-", (x,y) -> x-y, model), new RCPosition(4, 6));
		cp.add(new BinaryOperationButton("+", Double::sum, model), new RCPosition(5, 6));
		
		var pow = new BinaryOperationButton("x^n", Math::pow, (x,n) -> Math.pow(x, 1/n), this);
		buttonsNames.put(pow, "x^n");
		invertedButtonsNames.put(pow, "x^(1/n)");
		cp.add(pow, new RCPosition(5,1));
	}
	
	/**
	 * @return model object for this program
	 */
	public CalcModel getModel() {
		return model;
	}
	
	/**
	 * @return displayLabel
	 */
	public JLabel getDisplayLabel() {
		return displayLabel;
	}
	
	/**
	 * @return the state of the invertable calculator operations.
	 */
	public boolean isInversed() {
		return isInversed;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new Calculator().setVisible(true);
		});	
	}
}
