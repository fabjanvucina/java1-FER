package hr.fer.zemris.lsystems.impl;

/**
  * Generic collection interface.
 * 
 * @author fabjanvucina
 * @param <E> collection element type
 */
public interface Collection<E> {
	
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
	void add(E value);
	
	
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
	 * A default method which calls <code>processor.process()</code> for each element in this collection. 
	 * @param processor of objects of type E or any other superclass
	 */
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = createElementsGetter();
		
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	
	/**
	 * A method which adds all elements from the given collection into the current collection. The given collection remains unchanged.
	 * @param other collection of elements of type E or any other subclass
	 */
	default void addAll(Collection<? extends E> other){
		
		class LocalProcessor implements Processor<E> {
			
			@Override
			public void process(E value) {
				add(value);
			}
		}
		
		//procesor objekte mora gledati kroz naocale tipa E s obzirom da ce procesor dodavati elemente u kolekciju
		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);
		
	}
	
	
	/**
	 * Removes all elements from this collection.
	 */
	void clear();
	
	
	/**
	 * A factory method for <code>ElementsGetter</code> objects.
	 * @return new <code>ElementsGetter</code> object for this collection.
	 */
	ElementsGetter<E> createElementsGetter();
	
	
	/**
	 * A method which adds satisfiable elements from the other collection to this collection.
	 * @param other collection of elements of type E or any other subclass
	 * @param tester of objects of type E or any other superclass
	 * @throws <code>NullPointerException</code> if <code>other</code> or <code>tester</code> is <code>null</code>
	 */
	void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester);
}
