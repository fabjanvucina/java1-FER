package hr.fer.oprpp1.custom.collections;

import static java.lang.Math.ceil; 
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.abs;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple hashmap class.
 *  
 * @author fabjanvucina
 * @param <K> key type
 * @param <V> value type
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	/**
	 * Simple map entry class. If an entry gets assigned to an occupied slot, it is added to the end of the linked list at that slot.
	 * 
	 * @author fabjanvucina
	 *
	*/
	public static class TableEntry<K,V> {
		
		/**
		 * Key of the map entry
		 */
		private K key;
		
		/**
		 * Value of the map entry
		 */
		private V value;
		
		/**
		 * Reference of the next entry at the same slot
		 */
		private TableEntry<K,V> next;
		
		/**
		 * Public constructor.
		 * @param key
		 * @param value
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
			next = null;
		}
		
		
		/**
		 * @return key of the entry
		 */
		public K getKey() {
			return key;
		}
		
		
		/**
		 * @return value of the entry
		 */
		public V getValue() {
			return value;
		}

		
		/**
		 * Public setter for the entry value
		 */
		public void setValue(V value) {
			this.value = value;
		}


		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
	
	
	/**
	 * Reference to the slots array that the hashmap is using.
	 */
	private TableEntry<K,V>[] slotsArray;
	
	
	/**
	 * Number of entries stored in the map != number of slots
	 */
	private int size;
	
	
	/**
	 * A counter which keeps track on the number of structural modifications on this object.
	 */
	private long modificationCount;
	
	
	/**
	 * Public constructor for a hash map with 16 slots.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.slotsArray = (TableEntry<K,V>[]) new TableEntry[16];
		this.size = 0;
		this.modificationCount = 0;
	}
	
	
	/**
	 * Public constructor for a hash map with 16 slots.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) throw new IllegalArgumentException("Capacity should not be less than 1");
		
		//exponent is the ceiling of log2(capacity)
		double exponent = ceil(log10(capacity) / log10(2));
		int adaptedCapacity = (int)pow(2, exponent);
		
		this.slotsArray = (TableEntry<K,V>[]) new TableEntry[adaptedCapacity];
		this.size = 0;
		this.modificationCount = 0;
	}
	
	
	/**
	 * A private method which doubles the array capacity and returns the existing entries to their new slots
	 */
	@SuppressWarnings("unchecked")
	private void reallocateSlotsArray() {
		
		//create copy of all entries
		var entriesArray = this.toArray();
		int oldCapacity = this.slotsArray.length;
		
		//clear old slots array and allocate double memory
		this.clear();
		this.slotsArray = (TableEntry<K,V>[]) new TableEntry[oldCapacity * 2];
		
		//put existing entries into new slots
		for(int i=0, n=entriesArray.length; i<n; i++) {
			putEntry(entriesArray[i].key, entriesArray[i].value);
		}
	}
	
	
	/**
	 * A helper method for creating a new entry in the dictionary.
	 * @param key
	 * @param value
	 * @return <code>null</code> if there wasn't an existing entry associated with the same key, value of the existing entry otherwise
	 */
	private V putEntry(K key, V value) {
		
		//izracunaj iz kljuca slot u polju i dohvati head element na tom slotu
		int slotIndex = calculateSlotIndex(key);
		TableEntry<K,V> slotEntry = slotsArray[slotIndex];
		
		//slot je prazan
		if(slotEntry == null) {
			slotsArray[slotIndex] = new TableEntry<>(key, value);
			this.size++;
					
			return null;
		}
		
		//trazimo kljuc u ostatku liste
		while(slotEntry != null) {	
			
			//pronaden kljuc u listi
			if(slotEntry.key.equals(key)) {
				V oldValue = slotEntry.value;
				
				slotEntry.value = value;
				return oldValue;
			}
					
			//korak petlje
			if(slotEntry.next != null) {
				slotEntry = slotEntry.next;
			}
			
			//kljuc nije pronaden u listi, stavlja se na zadnje mjesto
			else {
				var newEntry = new TableEntry<>(key, value);
				
				slotEntry.next = newEntry;
				this.size++;
				return null;
			}	
		}
		
		
		//unnecessary return statement, compiler requested it
		return null;
			
	}
	
	
	/**
	 * A method which puts the key-value pair in the dictionary.
	 * @param key
	 * @param value
	 * @return <code>null</code> if there wasn't an existing entry associated with the same key, value of the existing entry otherwise
	 * @throws <code>NullPointerException</code> if <code>key == null</code>
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("Key of the entry should not be null");
		
		//ako popunjenost prevelika, realociraj
		if((double)this.size / this.slotsArray.length >= 0.75) {
			reallocateSlotsArray();
		}
		
		//private method call
		V oldValue = putEntry(key, value);
		
		this.modificationCount++;
		
		return oldValue;
	}
	
	
	/**
	 * @param key
	 * @return value of entry if it exists, <code>null</code> otherwise
	 * @throws <code>NullPointerException</code> if <code>key == null</code>
	 */
	public V get(Object key) {
		if(key == null) throw new NullPointerException("Key of the pair can not be null");
		
		//izracunaj iz kljuca slot u polju i dohvati head element na tom slotu
		int slotIndex = calculateSlotIndex(key);
		TableEntry<K,V> slotEntry = slotsArray[slotIndex];
		
		//trazimo kljuc u ostatku liste
		while(slotEntry != null) {
			//pronaden kljuc u listi
			if(slotEntry.key.equals(key)) {
				return slotEntry.value;
			}
								
			slotEntry = slotEntry.next;
		}
					
		//kljuc nije pronaden
		return null;
	}
	
	
	/**
	 * @return number of entries in the dictionary
	 */
	public int size() {
		return this.size;
	}
	
	
	/**
	 * A method which determines wheter the hashmap contains an entry with the specified key.
	 * @param key
	 * @return <code>true</code> if the map contains the key, <code>false</code> otherwise
	 */
	public boolean containsKey(Object key) {
		//kljuc ne moze biti null
		if(key == null) return false;
		
		//izracunaj iz kljuca slot u polju i dohvati head element na tom slotu
		int slotIndex = calculateSlotIndex(key);
		TableEntry<K,V> slotEntry = slotsArray[slotIndex];
		
		//trazimo kljuc u ostatku liste
		while(slotEntry != null) {				
			if(slotEntry.key.equals(key)) {
				return true;
			}
			
			//korak petlje
			slotEntry = slotEntry.next;
		}
		
		return false;
		
	}

	
	/**
	 * A method which determines wheter the hashmap contains an entry with the specified value.
	 * @param value
	 * @return <code>true</code> if the map contains the value, <code>false</code> otherwise
	 */
	public boolean containsValue(Object value) {
		
		//prolazimo svakim slotom u polju
		for(int i=0, n=slotsArray.length; i<n; i++) {
			
			TableEntry<K,V> slotEntry = slotsArray[i];
			
			//trazimo kljuc u ostatku liste
			while(slotEntry != null) {				
				if(slotEntry.value.equals(value) || (value == null && slotEntry.value == null)) {
					return true;
				}
				
				//korak petlje
				slotEntry = slotEntry.next;
			}
		}
		
		//value nije pronaden u hash mapi
		return false;
	}
	
	
	/**
	 * A method which removes an entry associated with the specified key from the hashmap if it exists.
	 * @param key
	 * @return <code>null</code> if the entry doesn't exist, value of the removed entry otherwise
	 * @throws <code>NullPointerException</code> if <code>key == null</code>
	 */
	public V remove(Object key) {
		if(key == null) throw new NullPointerException("Key of the pair can not be null");
		
		//izracunaj iz kljuca slot u polju i dohvati head element na tom slotu
		int slotIndex = calculateSlotIndex(key);
		TableEntry<K,V> slotEntry = slotsArray[slotIndex];
		
		//ako je trazeni entry na prvom mjestu na slotu
		if(slotEntry != null && slotEntry.key.equals(key)) {
			V oldValue = slotEntry.value;
			
			slotsArray[slotIndex] = slotEntry.next;
			
			this.size--;
			this.modificationCount++;
			
			return oldValue;
			
		}
		
		//trazimo kljuc u ostatku liste
		while(slotEntry != null) {	
			
			//pronaden kljuc u sredini listi(usporedujemo sljedeci element sa trazenim)
			if(slotEntry.next != null && slotEntry.next.key.equals(key)) {
				V oldValue = slotEntry.next.value;
				
				//izgubi referencu na entry koji brisemo
				slotEntry.next = slotEntry.next.next;

				this.size--;
				this.modificationCount++;
				
				return oldValue;
			}
					
			slotEntry = slotEntry.next;
		}
		
		
		this.modificationCount++;
		
		//the entry associated with the specified key doesn't exist
		return null;
	}

	/**
	 * @return <code>true</code> if dictionary is empty, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0 ? true : false;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		//prolazimo svakim slotom u polju
		for(int i=0, n=slotsArray.length; i<n; i++) {
			
			TableEntry<K,V> slotEntry = slotsArray[i];
			
			//trazimo kljuc u ostatku liste
			while(slotEntry != null) {				
				//dodaj string value od entrya
				sb.append(slotEntry);
				
				if(i != n-1 || (i == n-1 && slotEntry.next != null)) {
					sb.append(", ");
				}
				
				//korak petlje
				slotEntry = slotEntry.next;
			}
		}
		
		sb.append("]");
		return sb.toString();
	}
	
	
	/**
	 * @return the hashmap as an array
	 */
	public TableEntry<K,V>[] toArray() {
		@SuppressWarnings("unchecked")
		var mapArray = (TableEntry<K,V>[]) new TableEntry[this.size];
		
		//go into every slot
		for(int i=0, j=0, n=slotsArray.length; i<n; i++) {
			
			TableEntry<K,V> slotEntry = slotsArray[i];
			
			//dodajemo svaki entry iz liste
			while(slotEntry != null) {				
				mapArray[j++] = slotEntry;
				slotEntry = slotEntry.next;
			}
		}
		
		return mapArray;
	}
		
	
	/**
	 * A method which removes all entries from the map.
	 */
	public void clear() {
		for(int i=0, n=slotsArray.length; i<n; i++) {
			slotsArray[i] = null;
		}
		
		this.size = 0;
		this.modificationCount++;
	}
	
	
	/**
	 * @param key
	 * @return slot index
	 */
	private int calculateSlotIndex(Object key) {
		return abs(key.hashCode()) % slotsArray.length;
	}
	
	
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Hashmap iterator class.
	 * 
	 * @author fabjanvucina
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		/**
		 * The next entry that needs to be returned.
		 */
		private TableEntry<K, V> currentEntry;
		
		/**
		 * The next entry that needs to be returned.
		 */
		private int currentSlot;
		
		/**
		 * Number of remaining entries in the hashmap.
		 */
		private int noOfRemaining;
		
		/**
		 * Private variable which remembers the original number of modifications on the map at the moment of the <code>IteratorImpl</code> creation.
		 */
		private long savedModificationCount;
		
		/**
		 * Flag that remembers if it is allowed to remove current element
		 */
		private boolean cantRemove;
		
		/**
		 * Flag used to determine if a call of method next() is the first one
		 */
		private boolean start;
		
		/**
		 * Constructor for the implementation of an <code>Iterator</code> for <code>SimpleHashtable</code>
		 */
		public IteratorImpl() {
			this.currentEntry = slotsArray[0];
			this.currentSlot = 0;
			this.noOfRemaining = size;
			this.savedModificationCount = modificationCount;
			this.cantRemove = true;
			this.start = true;
		}
		
		@Override
		public boolean hasNext() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("You should create a new iterator after you modify the map.");
			}
			
			return noOfRemaining > 0;
		}
		
		@Override
		public SimpleHashtable.TableEntry<K,V> next() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("You should create a new iterator after you modify the map.");
			}
			if(noOfRemaining < 1) {
				throw new NoSuchElementException("All elements from the map have been returned.");
			}
			
			TableEntry<K, V> nextEntry;
			
			//get next entry only if this isn't first call of iter.next()
			if(!start) {
				currentEntry = currentEntry.next;
			}
			else {
				start = false;
			}
			
			//postoji sljedeci element u listi na trenutnom slotu
			if(currentEntry != null) {
				//return this element
				nextEntry = currentEntry;
			}
			
			//ne postoji sljedeci element u listi na trenutnom slotu -> trazimo neki sljedeci slot
			else  {
				
				//izvrti dok ne dodes do nepraznog slota
				while(slotsArray[++currentSlot] == null);
				
				currentEntry = slotsArray[currentSlot];
				
				//return this element
				nextEntry = currentEntry;
				
			}
			
			noOfRemaining--;
			cantRemove = false;
			
			return nextEntry;
		}
		
		@Override
		public void remove() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("You should create a new iterator after you modify the map.");
			}
			
			if(cantRemove) {
				throw new IllegalStateException("You should've called method next() before calling method remove().");
			}
		
			//izbrisi currentEntry
			SimpleHashtable.this.remove(currentEntry.key);
			
			//this lets this iterator to keep working
			savedModificationCount++;
			
			cantRemove = true;
		}
	}
}
