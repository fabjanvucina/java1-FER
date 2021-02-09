package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Demo class which uses <code>ObjectStack</code> class to perform postfix evaluation on expression. <br>
 * The user enters the expression as a command-line argument in following format: "&ltpostfix_expression&gt"
 * 
 * @author fabjanvucina
 */
public class StackDemo {

	public static void main(String[] args) {
		if(args.length == 0) throw new NullPointerException("You didn't define an expression as a command line argument.");
		
		StackDemo sd = new StackDemo();
		sd.postfixEvaluation(args[0]);

	}
	
	
	/**
	 * Method which performs postix evaluation on passed expression.
	 * @param expression
	 * @throws NullPointerException if <code>expression == null</code>
	 */
	public void postfixEvaluation(String expression) {
		if(expression == null) throw new NullPointerException("You didn't define an expression as a command line argument.");
		
		//split command line expression using 1 or more spaces as a delimiter
		String[] splitExpression = expression.trim().split("\\s+");
		ObjectStack stack = new ObjectStack();
		
		for(int i=0, n=splitExpression.length; i<n; i++) {
			
			//if element is operator
			//pop 2 preceeding numbers from the stack, perform specified operation and push result as string to stack
			if(isOperator(splitExpression[i])) {
				
				int first = 0, second = 0;
				try {
					second = Integer.parseInt((String) stack.pop());
				    first = Integer.parseInt((String) stack.pop());
				}
				catch(EmptyStackException ex) {
					throw new IllegalArgumentException("Your expression is invalid");
				}
				
				
				int result = performOperation(first, second, splitExpression[i]);
				stack.push(Integer.toString(result));
			}
			
			//push number string to stack
			else {
				stack.push(splitExpression[i]);
			}
		}
		
		//postfix evaluation should leave 1 value on the stack
		if(stack.size() != 1) throw new IllegalArgumentException("Your expression is invalid");
		
		int result = Integer.parseInt((String)stack.pop());
		System.out.println("Expression evaluates to " + result);
		
	}
	
	
	/**
	 * Method which determines if an input value is a mathematical operator(+, -, *, /, %).
	 * @param value
	 * @return true if <code>value</code> is an operator, <code>false</code> otherwise
	 * @throws NumberFormatException if <code>value</code> is not a number or one of the valid operators
	 */
	public boolean isOperator(String value) {
		//value is an operator
		if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/") || value.equals("%")) {
			return true;
		}
		
		//value is not an operator, if it isnt an integer, throw an error
		try { Integer.parseInt(value); } 
		catch(NumberFormatException ex) {
			throw new NumberFormatException("\"" + value + "\" is not a valid argument. Valid arguments are numbers and operators: + - * /");
		}
		
		//value is a number
		return false;
	}
	
	
	/**
	 * Method which performs integer operations on parameters.
	 * @param first
	 * @param second
	 * @param operator
	 * @return result
	 * @throws IllegalArgumentException in case of division by zero attempt
	 */
	public int performOperation(int first, int second, String operator) {
		
		if (operator.equals("+")) {
			return first + second;
		}

		else if (operator.equals("-")) {
			return first - second;
		}
	
		else if (operator.equals("*")) {
			return first * second;
		}
		
		else if (operator.equals("/")){
			if(second == 0) throw new IllegalArgumentException("You can not divide by zero");
			
			return (int)(first / second);
		}
		
		else {
			if(second == 0) throw new IllegalArgumentException("You can not divide by zero");
			
			return (int)(first % second);
		}
		
	}
}
