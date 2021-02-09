package hr.fer.oprpp1.custom.collections;

/**
 * Object stack class. It is an adapter for the <code>ArrayIndexedCollection</code> class.
 *  
 * @author fabjanvucina
 */
public class ObjectStack {
	
	/**
	 * A private instance of ArrayIndexedCollection which is used for actual element storage. The ObjectStack class provides
	 * an appropriate stack interface for users unaware of the internal implementation of those methods.
	 */
	private ArrayIndexedCollection adaptee = new ArrayIndexedCollection();
	
	
	/**
	 * Method which determines if the stack is empty.
	 * @return <code>true</code> if the stack contains no objects, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}
	
	
	/**
	 * @return number of stored elements
	 */
	public int size() {
		return adaptee.size();
	}
	
	
	/**
	 * Method which pushes given value to the stack.
	 * @param value
	 */
	public void push(Object value) {
		adaptee.add(value);
	}
	
	
	/**
	 * A method which pops element from the top of the stack.
	 * @return top of stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		if(this.size() == 0) throw new EmptyStackException("You can not pop an element from an empty stack.");
		
		Object top = adaptee.get(this.size() - 1);
		adaptee.remove(this.size() - 1);
		
		return top;
	}
	
	
	/**	 * 
	 * @return top of stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {
		if(this.size() == 0) throw new EmptyStackException("You can not pop an element from an empty stack.");
		
		Object top = adaptee.get(this.size() - 1);
		
		return top;
	}
	
	
	/**
	 * A method which removes all elements from the stack.
	 */
	public void clear() {
		adaptee.clear();
	}
}
