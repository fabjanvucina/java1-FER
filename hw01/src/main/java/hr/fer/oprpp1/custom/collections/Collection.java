package hr.fer.oprpp1.custom.collections;

/**
 * Generic collection class. It contains several unimplemented methods which need to be overridden by subclasses.
 * 
 * @author fabjanvucina
 */
public class Collection {
	
	/**
	 * A method which determines if the collection is empty.
	 * @return <code>true</code> if collection contains no objects, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return this.size() == 0 ? true : false;
	}
	
	
	/**
	 * @return number of stored elements
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the passed object into this collection.
	 * @param value
	 */
	public void add(Object value) {
		//do nothing
	}
	
	
	/**
	 * A method which determines if the collection contains the passed object using the equals method.
	 * It is OK to ask if collection contains <code>null</code>.
	 * @param value
	 * @return <code>true</code> if the collection contains given value, <code>false</code> otherwise
	 */
	public boolean contains(Object value)  {
		return false;
	}
	
	
	/**
	 * A method which removes an object from the collection that is equal to the passed object as determined by equals method.
	 * @param value
	 * @return <code>true</code> if the collection contains given value, <code>false</code> otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	
	/**
	 * A method which allocates new array with size equals to the size of this collections and fills it with collection content.
	 * This method never returns <code>null</code>.
	 * @return new array with the existing collection content
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * A method which calls <code>processor.process()</code> for each element of this collection. 
	 * @param processor
	 */
	public void forEach(Processor processor) {
		//do nothing
	}
	
	
	/**
	 * A method which adds all elements from the given collection into the current collection. The given collection remains unchanged.
	 * @param other
	 */
	public void addAll(Collection other) {
		
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				if(value != null) {
					add(value);
				}
			}
		}
		
		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);
	}
	
	
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		//do nothing
	}
}
