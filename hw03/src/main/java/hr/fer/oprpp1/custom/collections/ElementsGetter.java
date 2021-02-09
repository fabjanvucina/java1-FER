package hr.fer.oprpp1.custom.collections;

/**
 * Interface for fetching collection elements.
 * 
 * @author fabjanvucina
 * @param <E> collection element type
 */
public interface ElementsGetter<T> {

	/**
	 * @return <code>true</code> if the collection has at least one element that hasn't been returned to the user.
	 */
	boolean hasNextElement();
	
	/**
	 * @return next collection element
	 * @throws <code>NoSuchElementException</code> if there are no elements left
	 */
	T getNextElement();
	
	
	/**
	 * Consumer method which calls the <code>process</code> method of the passed <code>Processor</code> object for each of the remaining unread elements in the collection.
	 * @param processor
	 */
	void processRemaining(Processor<? super T> p);
}
