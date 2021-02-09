package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*;

public class PrimListModelTest {

	@Test
	public void getNextPrimeNumberTest() {
		PrimModelList listModel = new PrimModelList();
		
		listModel.next();
		listModel.next();
		listModel.next();
		listModel.next();
		
		assertEquals(1, listModel.getElementAt(0));
		assertEquals(2, listModel.getElementAt(1));
		assertEquals(3, listModel.getElementAt(2));
		assertEquals(5, listModel.getElementAt(3));
		assertEquals(7, listModel.getElementAt(4));
	}
	
	
	@Test
	public void isPrimeTest() {
		PrimModelList listModel = new PrimModelList();
		
		assertFalse(listModel.isPrime(-2));
		assertTrue(listModel.isPrime(59));
		assertFalse(listModel.isPrime(60));
		assertTrue(listModel.isPrime(61));
	}
}
