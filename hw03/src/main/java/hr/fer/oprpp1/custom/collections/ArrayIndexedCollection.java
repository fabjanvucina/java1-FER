package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * An array-indexed collection class. Duplicate elements are allowed. Storage of <code>null</code> references is not allowed.
 * 
 * @author fabjanvucina
 * @param <E> collection element type
 */
public class ArrayIndexedCollection<E> implements List<E> {
	
	/**
	 *  Current size of collection
	 */
	private int size;
	
	/**
	 * An array of object references whose length is determined by capacity argument in the constructor
	 */
	private E[] elements;
	
	
	/**
	 * A counter which keeps track on the number of structural modifications on this object.
	 */
	private long modificationCount;
	
	
	/**
	 * Public constructor.
	 * @param initialCapacity
	 * @throws IllegalArgumentException if <code>initialCapacity &lt 1</code>
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Initial capacity should not be less than 1");
		
		this.elements = (E[]) new Object[initialCapacity];
		
		this.size = 0;
		this.modificationCount = 0;
	}
	
	
	/**
	 * Default constructor which initializes the objects array to a capacity of 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	
	/**
	 * A constructor which copies elements from the passed collection into the newly created collection.
	 * The initial capacity is <code>max(other.size(), initialCapacity)</code>
	 * 
	 * @param other collection whose elements we will copy into the new collection
	 * @param initialCapacity capacity of the objects array if it's larger than the size of the passed collection
	 * @throws NullPointerException if reference to the passed collection is <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends E> other, int initialCapacity) {
		if(other == null) throw new NullPointerException("You should not pass null as a Collection reference");
		
		this.elements = (E[])new Object[Math.max(other.size(), initialCapacity)];
		this.addAll(other);
		
		this.size = other.size();
		this.modificationCount = 0;
	}
	
	
	/**
	 * Public constructor which copies elements from the passed collection into the newly created collection.
	 * The initial capacity is <code>max(other.size(), initialCapacity)</code>
	 * @param other
	 * @param initialCapacity
	 * @throws NullPointerException if <code>other == null</code>
	 */
	public ArrayIndexedCollection(Collection<? extends E> other) {
		this(other, 16);
	}
	
	
	/**
	 * @return collection capacity
	 */
	public int getCapacity() {
		return this.elements.length;
	}


	/**
	 * A private method which doubles the array capacity and copies the existing elements into the new array.
	 */
	@SuppressWarnings("unchecked")
	private void reallocateElementsArray() {
		var elementsCopy = this.elements;
		
		this.elements = (E[]) new Object[elementsCopy.length * 2];
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
	public void add(E value) {
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
	 * @throws NullPointerException if value == <code>null</code>
	 */
	@Override
	public void insert(E value, int position) {
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
	public E get(int index) {
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
			if(element != null && element.equals(value)) {
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
		Object[] elementsArray = new Object[size];
		
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
	public ElementsGetter<E> createElementsGetter() {
		return new ArrayElementsGetter<E>(this);
	}
	
	
	/**
	 * Implementation of <code>ElementsGetter</code> interface.
	 *  
	 * @author fabjanvucina
	 */
	private static class ArrayElementsGetter<E> implements ElementsGetter<E> {
		
		/**
		 * Private variable which keeps the reference of the collection for which we are creating n instance of an <code>ArrayElementsGetter</code>.
		 */
		private ArrayIndexedCollection<E> collection;
		
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
		private ArrayElementsGetter(ArrayIndexedCollection<E> collection) {
			this.collection = collection;
			currentElementIndex = 0;
			noOfRemainingElements = collection.size;
			savedModificationCount = collection.modificationCount;
		}
		

		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException("You should not use the same ElementsGetter object if you modified the original collection object.");
			return noOfRemainingElements > 0;
		}

		
		@Override
		public E getNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException("You should not use the same ElementsGetter object if you modified the original collection object.");
			if(noOfRemainingElements < 1) throw new NoSuchElementException("All elements from the collection have been returned.");
			
			noOfRemainingElements--;
			return collection.get(currentElementIndex++);
		}


		@Override
		public void processRemaining(Processor<? super E> p) {
			while(hasNextElement()) {
				p.process(getNextElement());
			}

		}
		
	}


	@Override
	public void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester) {
		if(col == null) throw new NullPointerException("The collection you passed should not be null");
		if(tester == null) throw new NullPointerException("The collection you passed should not be null");
		
		ElementsGetter<? extends E> getter = col.createElementsGetter();
		
		while(getter.hasNextElement()) {
			var nextElement = getter.getNextElement();
			
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
		if (!(obj instanceof ArrayIndexedCollection<?>))
			return false;
		@SuppressWarnings("unchecked")
		ArrayIndexedCollection<E> other = (ArrayIndexedCollection<E>) obj;
		if (!Arrays.deepEquals(elements, other.elements))
			return false;
		if (size != other.size)
			return false;
		return true;
	}	
}
