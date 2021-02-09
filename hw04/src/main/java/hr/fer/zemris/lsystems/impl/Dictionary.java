package hr.fer.zemris.lsystems.impl;

/**
 * Simple dictionary(map) class.
 * 
 * @author fabjanvucina
 * @param <K> key type
 * @param <V> value type
 */
public class Dictionary<K,V> {

	/**
	 * A private static class which represents a key-value pair.
	 * 
	 * @author fabjanvucina
	 * @param <K> key type
	 * @param <V> value type
	*/
	private static class Pair<K,V> {

		/**
		 * key of the pair
		 */
		private K key;
		
		/**
		 * value of the pair
		 */
		private V value;
		
		/**
		 * Public constructor.
		 * @param key
		 * @param value
		 * @throws <code>NullPointerException</code> if <code>key</code> is <code>null</code>
		 */
		public Pair(K key, V value) {
			if(key == null) throw new NullPointerException("Key of the pair should not be null");
			
			this.key = key;
			this.value = value;
		}
	}
	
	
	/**
	 * A private instance of ArrayIndexedCollection which is used for storage of map entries.
	 */
	private ArrayIndexedCollection<Pair<K,V>> entries;
	
	
	/**
	 * Default constructor.
	 */
	public Dictionary() {
		this.entries = new ArrayIndexedCollection<>();
	}
	
	
	/**
	 * @return <code>true</code> if dictionary is empty, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}
	
	
	/**
	 * @return number of entries in the dictionary
	 */
	public int size() {
		return entries.size();
	}
	
	
	/**
	 * A method which removes all entries from the dictionary.
	 */
	void clear() {
		entries.clear();
	}
	
	
	/**
	 * A method which puts the key-value pair in the dictionary.
	 * @param key
	 * @param value
	 * @return <code>null</code> if there wasn't an existing entry associated with the same key, value of the existing entry otherwise
	 * @throws <code>NullPointerException</code> if <code>key == null</code>
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("Key of the pair should not be null");
		
		var elementsGetter = entries.createElementsGetter();
		
		//check if key exists in map
		while(elementsGetter.hasNextElement()) {
			Pair<K,V> entry = elementsGetter.getNextElement();
			
			//key exists in map
			if(entry.key.equals(key)) {
				V oldValue = entry.value;
				
				entry.value = value;
				return oldValue;
				
			}
		}
		
		//key doesn't exist in map
		Pair<K,V> newEntry = new Pair<>(key, value);
		entries.add(newEntry);
		return null;
	}
	
	
	/**
	 * @param key
	 * @return value of entry if it exists, <code>null</code> otherwise
	 * @throws <code>NullPointerException</code> if <code>key == null</code>
	 */
	public V get(Object key) {		
		if(key == null) throw new NullPointerException("Key of the pair can not be null");
		
		var elementsGetter = entries.createElementsGetter();
		
		//check if key ecists in map
		while(elementsGetter.hasNextElement()) {
			Pair<K,V> entry = elementsGetter.getNextElement();
			
			//key exists in map
			if(entry.key.equals(key)) {
				return entry.value;
				
			}
		}
		
		//key doesn't exist in map
		return null;
	}
	
	
	/**
	 * A method which removes an entry associated with the specified key.
	 * @param key
	 * @return <code>null</code> if there wasn't an existing entry associated with the same key, value of the existing entry otherwise
	 * @throws <code>NullPointerException</code> if <code>key == null</code>
	 */
	public V remove(K key) {	
		if(key == null) throw new NullPointerException("Key of the pair can not be null");
		
		var elementsGetter = entries.createElementsGetter();
		
		//check if key exists in map
		while(elementsGetter.hasNextElement()) {
			Pair<K,V> entry = elementsGetter.getNextElement();
			
			//key exists in map
			if(entry.key.equals(key)) {
				V oldValue = entry.value;
				
				entries.remove(entry);
				return oldValue;
				
			}
		}
		
		//key doesn't exist in map
		return null;
	}	
}
