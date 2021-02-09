package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList; 
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Data model class which represents a list of prime numbers which can be passed to a view component.
 * 
 * @author fabjanvucina
 */
public class PrimModelList implements ListModel<Integer> {

	/**
	 * Current prime number in the list model.
	 */
	private int currentPrimeNumber;
	
	/**
	 * View components observing this list model.
	 */
	private List<ListDataListener> observers;
	
	/**
	 * Current data in the list model.
	 */
	private List<Integer> addedPrimeNumbers;
	
	/**
	 * Public constructor
	 * @param currentPrimeNumber
	 */
	public PrimModelList() {
		currentPrimeNumber = 0;
		observers = new ArrayList<>();
		addedPrimeNumbers = new ArrayList<>();
		next();
	}

	@Override
	public int getSize() {
		return addedPrimeNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return addedPrimeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);
	}
	
	/**
	 * A method which adds the next prime number to the model and notifies all observers.
	 */
	public void next() {
		
		//get next prime number and add it to the list
		currentPrimeNumber = getNextPrimeNumber();
		addedPrimeNumbers.add(currentPrimeNumber);
		
		//event that represents adding a new prime number
		ListDataEvent e = new ListDataEvent(this, 
											ListDataEvent.INTERVAL_ADDED, 
											addedPrimeNumbers.size()-1, 
											addedPrimeNumbers.size()-1);
		
		//notify all observers of the event
		for(ListDataListener observer : observers) {
			observer.intervalAdded(e);
		}
	}
	
	/**
	 * @return next prime number
	 */
	public int getNextPrimeNumber() {
		
		//base cases
        if(currentPrimeNumber < 1) return 1; 
        if(currentPrimeNumber == 1) return 2; 
      
        int nextPrime = currentPrimeNumber;  
        boolean found = false;  
      
        //return the next number that returns true for isPrime
        while(!found) {   
            if (isPrime(++nextPrime))  
                found = true;  
        }  
      
        return nextPrime;  
	}
	
	/**
	 * A method which checks if the passed argument is a prime number. It utilizes a popular efficient algorithm.
	 * @param n
	 * @return <code>true</code> if <code>n</code> is prime, <code>false</code> otherwise.
	 */
	public boolean isPrime(int n) {  
		
        // base cases
        if(n <= 1) return false;  
        if(n <= 3) return true;  
          
        //eliminate all numbers divisible by 2 and 3
        if(n % 2 == 0 || n % 3 == 0) return false;  
          
        //we can start from 5, we eliminated 0, 1, 2, 3 and 4
        for(int i = 5; i * i <= n; i = i + 6)  {
        	if (n % i == 0 || n % (i + 2) == 0)  
                return false;  
        }
            
        //haven't found a factor that n is divisible with  
        return true;  
    }  
}
