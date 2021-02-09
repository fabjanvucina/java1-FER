package hr.fer.zemris.lsystems.impl;

/**
 * Object tester functional interface.
 * 
 * @author fabjanvucina
 * @param <T> testing object type
 */
public interface Tester<T> {
	
	/**
	 * @param obj
	 * @return <code>true</code> if <code>obj</code> is acceptable, <code>false</code> otherwise.
	 */
	boolean test(T obj);
}
