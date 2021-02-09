package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * A class created with the purpose of performing junit tests on the class <code>LinkedListIndexedCollection</code>
 * 
 * @author fabjanvucina
 *
 */
public class LinkedListIndexedCollectionTest {

	@Test
	public void testConstructorNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testAddMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new LinkedListIndexedCollection()).add(null));
	}
	
	@Test
	public void testAddMethodShouldAddElement(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals("b", test.get(1));
	}
	
	@Test
	public void testRemoveMethodInvalidIndexShouldThrow(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> test.remove(3));
	}
	
	@Test
	public void testRemoveMethodShouldRemoveAtIndex(){
		var test1 = new LinkedListIndexedCollection();
		test1.add("a");
		test1.add("b");
		test1.remove(0);
		
		var test2 = new LinkedListIndexedCollection();
		test2.add("b");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testInsertMethodNullArgumentShouldThrow(){
		assertThrows(NullPointerException.class, () -> (new LinkedListIndexedCollection()).insert(null, 0));
	}
	
	@Test
	public void testInsertMethodInvalidIndexShouldThrow(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> test.remove(4));
	}
	
	@Test
	public void testInsertMethodShouldInsertAtStartOfEmptyCollection(){
		var test1 = new LinkedListIndexedCollection();
		test1.insert("a", 0);
		
		var test2 = new LinkedListIndexedCollection();
		test2.add("a");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testInsertMethodShouldInsertAtStartOfNonEmptyCollection(){
		var test1 = new LinkedListIndexedCollection();
		test1.add("b");
		test1.insert("a", 0);
		
		var test2 = new LinkedListIndexedCollection();
		test2.add("a");
		test2.add("b");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testInsertMethodShouldInsertAtMiddleOfCollection(){
		var test1 = new LinkedListIndexedCollection();
		test1.add("a");
		test1.add("c");
		test1.add("d");
		test1.insert("b", 1);
		
		var test2 = new LinkedListIndexedCollection();
		test2.add("a");
		test2.add("b");
		test2.add("c");
		test2.add("d");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testInsertMethodShouldInsertBeforeLastElementOfCollection(){
		var test1 = new LinkedListIndexedCollection();
		test1.add("a");
		test1.add("c");
		test1.insert("b", 1);
		
		var test2 = new LinkedListIndexedCollection();
		test2.add("a");
		test2.add("b");
		test2.add("c");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testInsertMethodShouldInsertAfterLastElementOfCollection(){
		var test1 = new LinkedListIndexedCollection();
		test1.add("a");
		test1.add("b");
		test1.insert("c", 2);
		
		var test2 = new LinkedListIndexedCollection();
		test2.add("a");
		test2.add("b");
		test2.add("c");
		
		assertArrayEquals(test1.toArray(), test2.toArray());
	}
	
	@Test
	public void testGetMethodInvalidIndexShouldThrow(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> test.get(3));
	}
	
	@Test
	public void testGetMethodShouldReturnElement(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals("c", test.get(2));
	}
	
	@Test
	public void testIndexOfNullMethodShouldReturn(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals(-1, test.indexOf(null));
	}
	
	@Test
	public void testIndexOfExistingElementMethodShouldReturn(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals(1, test.indexOf("b"));
	}
	
	@Test
	public void testIndexOfNonExistingElementMethodShouldReturn(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		
		assertEquals(-1, test.indexOf("d"));
	}
	
	@Test
	public void testClearMethodShouldRemoveAllElements(){
		var test = new LinkedListIndexedCollection();
		test.add("a");
		test.add("b");
		test.add("c");
		test.clear();
		
		assertEquals(0, test.size());
	}
}
