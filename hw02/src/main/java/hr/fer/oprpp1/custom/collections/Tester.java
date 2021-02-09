package hr.fer.oprpp1.custom.collections;

/**
 * Object tester functional interface.
 * 
 * @author fabjanvucina
 */
public interface Tester {
	
	/**
	 * @param obj
	 * @return <code>true</code> if <code>obj</code> is acceptable, <code>false</code> otherwise.
	 */
	boolean test(Object obj);
}
