package hr.fer.oprpp1.custom.collections;

/**
 * List interface which specializes the <code>Collection</code> interface.
 * 
 * @author fabjanvucina
 */
public interface List extends Collection {
	
	/**
	 * @param index
	 * @return element at the specified index
	 */
	Object get(int index);
	
	
	/**
	 * A method which inserts <code>value</code> at the specified index.
	 * @param value
	 * @param index
	 */
	void insert(Object value, int index);
	
	
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
