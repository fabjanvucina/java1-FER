package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * An array-indexed collection class. Duplicate elements are allowed. Storage of <code>null</code> references is not allowed.
 * 
 * @author fabjanvucina
 */
public class ArrayIndexedCollection implements List {
	
	/**
	 *  Current size of collection
	 */
	private int size;
	
	/**
	 * An array of object references whose length is determined by capacity argument in the constructor
	 */
	private Object[] elements;
	
	
	/**
	 * A counter which keeps track on the number of structural modifications on this object.
	 */
	private long modificationCount = 0;
	
	
	/**
	 * Public constructor.
	 * @param initialCapacity
	 * @throws IllegalArgumentException if <code>initialCapacity &lt 1</code>
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Initial capacity should not be less than 1");
		
		this.elements = new Object[initialCapacity];
		this.size = 0;
	}
	
	
	/**
	 * Default constructor which initializes the objects array to a capacity of 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	
	/**
	 * Public constructor which copies elements from the passed collection into the newly created collection.
	 * The initial capacity is <code>max(other.size(), initialCapacity)</code>
	 * @param other
	 * @param initialCapacity
	 * @throws NullPointerException if <code>other == null</code>
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) throw new NullPointerException("You should not pass null as a Collection reference");
		
		this.elements = new Object[Math.max(other.size(), initialCapacity)];
		this.addAll(other);
		this.size = other.size();
	}
	
	
	/**
	 * Public constructor which copies elements from the passed collection into the newly created collection.
	 * The initial capacity is <code>max(other.size(), 16)</code>
	 * @param other
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}


	/**
	 * A private method which doubles the array capacity and copies the existing elements into the new array.
	 */
	private void reallocateElementsArray() {
		var elementsCopy = this.elements;
		
		this.elements = new Object[elementsCopy.length * 2];
		for(int i=0, n=elementsCopy.length; i<n; i++) {
			this.elements[i] = elementsCopy[i];
		}
	}
	
	
	@Override
	public int size() {
		return this.size;
	}
	

	/**
	 * @throws NullPointerException if value == <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		if (this.size == this.elements.length) {
			this.reallocateElementsArray();
		}		
		this.elements[this.size++] = value;
		
		modificationCount++;
	}


	/**
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
	@Override
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index should be in [0, " + (this.size - 1) + "] range.");
		
		for(int i=index; i<this.size-1; i++) {
			this.elements[i] = this.elements[i+1];
		}
		this.elements[this.size - 1] = null;
		this.size--;
		
		modificationCount--;
	}
	
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	@Override
	public boolean remove(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		for(int i=0; i<this.size; i++) {
			if(this.elements[i].equals(value)) {
				this.remove(i);
				return true;
			}
		}
		
		modificationCount--;
		
		return false;
	}
	
	/**
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size]</code> range
	 * @throws NullPointerException if <code>value == null</code>
	 */
	@Override
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException("Index should be in [0, " + this.size + "] range.");
	    if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
	    if (this.size == this.elements.length) {
			this.reallocateElementsArray();
		}
		
		for(int i=this.size; i>position; i--) {
			this.elements[i] = this.elements[i-1];
		}
		this.elements[position] = value;
		this.size++;
		
		modificationCount++;
	}
	
	
	/**
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
	@Override
	public Object get(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index should be in [0, " + (this.size - 1) + "] range.");
		
		return this.elements[index];
	}
	
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	@Override
	public boolean contains(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		for(Object element : this.elements) {
			if(element.equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		for(int i=0; i<this.size; i++) {
			if(this.elements[i].equals(value)) {
				return i;
			}
		}
		
		return -1;
	}


	@Override
	public Object[] toArray() {
		Object[] elementsArray = new Object[this.size];
		
		for(int i=0; i<size; i++) {
			elementsArray[i] = this.elements[i];
		}
		return elementsArray;
	}
	

	@Override
	public void clear() {
		for(int i=0; i<this.size; i++) {
			this.elements[i] = null;
		}
		 
		this.size = 0;
		
		modificationCount++;
	}
	

	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayElementsGetter(this);
	}
	
	
	/**
	 * Implementation of <code>ElementsGetter</code> interface.
	 *  
	 * @author fabjanvucina
	 */
	private static class ArrayElementsGetter implements ElementsGetter {
		
		/**
		 * Private variable which keeps the reference of the collection for which we are creating n instance of an <code>ArrayElementsGetter</code>.
		 */
		private ArrayIndexedCollection collection;
		
		/**
		 * Index of element that is to be returned next.
		 */
		private int currentElementIndex;
		
		/**
		 * Number of unreturned elements in the collection.
		 */
		private int noOfRemainingElements;
		
		/**
		 * Private variable which remembers the original number of modifications on the collection object at the moment of the <code>ArrayElementsGetter</code> creation.
		 */
		private long savedModificationCount;
		
		
		/**
		 * Public constructor.
		 * @param collection
		 */
		private ArrayElementsGetter(ArrayIndexedCollection collection) {
			this.collection = collection;
			currentElementIndex = 0;
			noOfRemainingElements = collection.size;
			savedModificationCount = collection.modificationCount;
		}
		

		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) 
				throw new ConcurrentModificationException("You should not use the same ElementsGetter object if you modified the original collection object.");
			return noOfRemainingElements > 0;
		}

		
		@Override
		public Object getNextElement() {
			if(savedModificationCount != collection.modificationCount) 
				throw new ConcurrentModificationException("You should not use the same ElementsGetter object if you modified the original collection object.");
			if(noOfRemainingElements < 1) 
				throw new NoSuchElementException("All elements from the collection have been returned.");
			
			noOfRemainingElements--;
			return collection.get(currentElementIndex++);
		}


		@Override
		public void processRemaining(Processor p) {
			while(hasNextElement()) {
				p.process(getNextElement());
			}
		}	
	}


	@Override
	public void addAllSatisfying(Collection col, Tester tester) {
		if(col == null) throw new NullPointerException("The collection you passed should not be null");
		if(tester == null) throw new NullPointerException("The collection you passed should not be null");
		
		ElementsGetter getter = col.createElementsGetter();
		
		while(getter.hasNextElement()) {
			Object nextElement = getter.getNextElement();
			
			if(tester.test(nextElement)) {
				this.add(nextElement);
			}
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + size;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ArrayIndexedCollection))
			return false;
		ArrayIndexedCollection other = (ArrayIndexedCollection) obj;
		if (!Arrays.deepEquals(elements, other.elements))
			return false;
		if (size != other.size)
			return false;
		return true;
	}	
}
