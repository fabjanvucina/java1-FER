package hr.fer.zemris.lsystems.impl;

/**
 * A class which represents the current stack of all turtle objects that are actively used to generate a Lindermayer system
 * 
 * @author fabjanvucina
 */
public class Context {

	/**
	 * Stack of active turtles
	 */
	private ObjectStack<TurtleState> turtleStateStack;
	
	
	/**
	 * Default constructor.
	 */
	public Context() {
		turtleStateStack = new ObjectStack<>();
	}
	
	/**
	 * @return current turtle state
	 */
	public TurtleState getCurrentState() {
		return turtleStateStack.peek();
	}
	
	/**
	 * A method which pushes a new turtle to the stack.
	 * @param state
	 */
	public void pushState(TurtleState state) {
		turtleStateStack.push(state);
	}
		
	/**
	 * A method which pops the current turtle state
	 */
	public void popState() {
		turtleStateStack.pop();
	}
}
