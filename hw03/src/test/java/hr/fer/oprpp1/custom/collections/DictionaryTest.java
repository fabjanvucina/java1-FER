package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test; 

import static org.junit.jupiter.api.Assertions.*; 

public class DictionaryTest {
	
	@Test
	public void testIsEmpty(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testSize(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		dictionary.put(1, "a");
		dictionary.put(2, "b");
		
		assertEquals(2, dictionary.size());
	}
	
	@Test
	public void testClear(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		dictionary.put(1, "a");
		dictionary.put(2, "b");
		dictionary.clear();
		
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testPutNull(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		
		assertThrows(NullPointerException.class, () -> dictionary.put(null, "value"));
	}
	
	@Test
	public void testPutNewKey(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		
		assertEquals(null, dictionary.put(1, "a"));
		assertEquals("a", dictionary.get(1));
		assertEquals(1, dictionary.size());
	}
	
	@Test
	public void testPutExistingKey(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		dictionary.put(1, "a");
		
		assertEquals("a", dictionary.put(1, "b"));
		assertEquals("b", dictionary.get(1));
		assertEquals(1, dictionary.size());
	}
	
	@Test
	public void testGetNull(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		
		assertThrows(NullPointerException.class, () -> dictionary.get(null));
	}
	
	@Test
	public void testGetExistingEntry(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		dictionary.put(1, "a");
		
		assertEquals("a", dictionary.get(1));
		assertEquals(1, dictionary.size());
	}
	
	@Test
	public void testGetNonExistingEntry(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		
		assertEquals(null, dictionary.get(1));
	}
	
	@Test
	public void testWrongKeyType(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		dictionary.put(1, "a");
		
		assertEquals(null, dictionary.get("1"));
	}
	
	@Test
	public void testRemoveExistingEntry(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		dictionary.put(1, "a");
		
		dictionary.remove(1);
		
		assertEquals(null, dictionary.get(1));
		assertEquals(0, dictionary.size());
	}
	
	@Test
	public void testRemoveNonExistingEntry(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();	
		dictionary.put(1, "a");
		
		assertEquals(null, dictionary.get(2));
	}
}
