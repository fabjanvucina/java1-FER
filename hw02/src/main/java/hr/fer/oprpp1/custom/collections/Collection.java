package hr.fer.oprpp1.custom.collections;

/**
 * Generic collection interface.
 * 
 * @author fabjanvucina
 */
public interface Collection {
	
	/**
	 * A default method which determines if the collection is empty.
	 * @return <code>true</code> if collection contains no objects, <code>false</code> otherwise
	 */
    default boolean isEmpty() {
		return this.size() == 0 ? true : false;
	}
	
	
	/**
	 * @return number of stored elements
	 */
    int size();
    
	
	/**
	 * Adds the passed object into this collection.
	 * @param value
	 */
	void add(Object value);
	
	
	/**
	 * A method which determines if the collection contains the passed object using the equals method.
	 * It is OK to ask if collection contains <code>null</code>.
	 * @param value 
	 * @return <code>true</code> if the collection contains given value, <code>false</code> otherwise
	 */
	public boolean contains(Object value);
	
	
	/**
	 * A method which removes an object from the collection that is equal to the passed object as determined by equals method.
	 * @param value
	 * @return <code>true</code> if the collection contains given value, <code>false</code> otherwise
	 */
	boolean remove(Object value);
	
	
	/**
	 * A method which allocates new array with size equals to the size of this collections and fills it with collection content.
	 * This method never returns <code>null</code>.
	 * @return new array with the existing collection content
	 */
	Object[] toArray();
	
	
	/**
	 * A factory method for <code>ElementsGetter</code> objects.
	 * @return new <code>ElementsGetter</code> object for this collection.
	 */
	ElementsGetter createElementsGetter();
	
	
	/**
	 * A default method which calls <code>processor.process()</code> for each element in this collection. 
	 * @param processor 
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	
	/**
	 * A method which adds all elements from the given collection into the current collection. The given collection remains unchanged.
	 * @param other
	 */
	default void addAll(Collection other){
		
		class LocalProcessor implements Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);	
	}
	
	
	/**
	 * A method which adds satisfiable elements from the other collection to this collection.
	 * @param other
	 * @param tester
	 * @throws <code>NullPointerException</code> if <code>other</code> or <code>tester</code> is <code>null</code>
	 */
	void addAllSatisfying(Collection other, Tester tester);
	
	
	/**
	 * Removes all elements from this collection.
	 */
	void clear();
}
