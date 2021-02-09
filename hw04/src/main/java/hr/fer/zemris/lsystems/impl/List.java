package hr.fer.zemris.lsystems.impl;

/**
 * List interface which specializes the <code>Collection</code> interface.
 * 
 * @author fabjanvucina
 * @param <E> collection element type
 *
 */
public interface List<E> extends Collection<E> {
	
	/**
	 * @param index
	 * @return element at the specified index
	 */
	E get(int index);
	
	
	/**
	 * A method which inserts <code>value</code> at the specified index.
	 * @param value
	 * @param index
	 */
	void insert(E value, int position);
	
	
	/**
	 * @param value
	 * @return index of the first occurence if the element is found, -1 otherwise
	 */
	int indexOf(Object value);
	
	
	/**
	 * A method which removes the element at the specified index.
	 * @param index
	 */
	void remove(int index);
}
