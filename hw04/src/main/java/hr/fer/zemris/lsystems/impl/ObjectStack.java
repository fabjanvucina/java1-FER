package hr.fer.zemris.lsystems.impl;

/**
 *  A class which provides an object representation of a stack with all it's natural methods.
 *  
 * @author fabjanvucina
 * 
 * @param <E> collection element type
 */
public class ObjectStack<E> {
	
	/**
	 * A private instance of ArrayIndexedCollection which is used for actual element storage. The ObjectStack class provides
	 * an appropriate stack interface for users unaware of the internal implementation of those methods.
	 */
	private ArrayIndexedCollection<E> adaptee;
	
	
	/**
	 * Default constructor for the stack object.
	 */
	public ObjectStack() {
		this.adaptee = new ArrayIndexedCollection<E>();
	}
	
	
	/**
	 * A method which determines if the stack is empty.
	 * 
	 * @return true if the stack contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}
	
	
	/**
	 * A method which returns the number of currently stored objects in this stack
	 * 
	 * @return size of the stack
	 */
	public int size() {
		return adaptee.size();
	}
	
	
	/**
	 * A method which pushes given value to the stack.
	 * 
	 * @param value value to be pushed to the stack
	 */
	public void push(E value) {
		adaptee.add(value);
	}
	
	
	/**
	 * A method which removes last value pushed to the stack from the stack and returns it.
	 * 
	 * @return last value pushed on to the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public E pop() {
		if(this.size() == 0) throw new EmptyStackException("You can not pop an element from an empty stack.");
		
		E top = adaptee.get(this.size() - 1);
		adaptee.remove(this.size() - 1);
		
		return top;
	}
	
	
	/**
	 * A method which returns the  last value pushed to the stack.
	 * 
	 * @return last value pushed on to the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public E peek() {
		if(this.size() == 0) throw new EmptyStackException("You can not pop an element from an empty stack.");
		
		E top = adaptee.get(this.size() - 1);
		
		return top;
	}
	
	
	/**
	 * A method which removes all elements from the stack.
	 */
	public void clear() {
		adaptee.clear();
	}
	
}
