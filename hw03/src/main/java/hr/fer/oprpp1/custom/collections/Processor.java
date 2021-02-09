package hr.fer.oprpp1.custom.collections;

/**
 * Object processor functional interface.
 * 
 * @author fabjanvucina
 * @param <T> processing object type
 */
public interface Processor<T> {
	
	/**
	 * @param value Passed object
	 */
	 void process(T value);
}
