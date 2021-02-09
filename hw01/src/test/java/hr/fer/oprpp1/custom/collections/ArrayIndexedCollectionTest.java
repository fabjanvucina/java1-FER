package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * A class created with the purpose of performing junit tests on the class <code>ArrayIndexedCollection</code>
 * 
 * @author fabjanvucina
 *
 */
public class ArrayIndexedCollectionTest {
	
	@Test
	public void testConstructorTooSmallCapacityShouldThrow(){
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testConstructorNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testConstructorShouldSetLength(){
		assertEquals(16, (new ArrayIndexedCollection()).toArray().length);
	}
	
	@Test
	public void testAddMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new ArrayIndexedCollection()).add(null));
	}
	
	@Test
	public void testAddMethodShouldReallocateArray(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals(4, test.toArray().length);
	}
	
	@Test
	public void testRemoveMethodInvalidIndexShouldThrow(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> test.remove(3));
	}
	
	@Test
	public void testRemoveMethodShouldRemoveAtIndex(){
		var test1 = new ArrayIndexedCollection(2);
		test1.add("a");
		test1.add("b");
		test1.remove(0);
		
		var test2 = new ArrayIndexedCollection(2);
		test2.add("b");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testInsertMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new ArrayIndexedCollection()).insert(null, 0));
	}
	
	@Test
	public void testInsertMethodInvalidIndexShouldThrow(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> test.insert("e", 4));
	}
	
	@Test
	public void testInsertMethodShouldInsertAtPosition(){
		var test1 = new ArrayIndexedCollection(2);
		test1.add("a");
		test1.add("b");
		test1.add("d");
		test1.insert("c", 2);
		
		var test2 = new ArrayIndexedCollection(2);
		test2.add("a");
		test2.add("b");
		test2.add("c");
		test2.add("d");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testGetMethodInvalidIndexShouldThrow(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> test.get(3));
	}
	
	@Test
	public void testGetMethodShouldReturnElement(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals("c", test.get(2));
	}
	
	@Test
	public void testIndexOfNullMethodShouldReturn(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals(-1, test.indexOf(null));
	}
	
	@Test
	public void testIndexOfExistingElementMethodShouldReturn(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals(1, test.indexOf("b"));
	}
	
	@Test
	public void testIndexOfNonExistingElementMethodShouldReturn(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals(-1, test.indexOf("d"));
	}
	
	@Test
	public void testClearMethodShouldRemoveAllElements(){
		var test = new ArrayIndexedCollection(2);
		test.add("a");
		test.add("b");
		test.add("c");
		test.clear();
		
		assertEquals(0, test.size());
	}
}
