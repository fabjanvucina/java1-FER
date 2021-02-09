package hr.fer.oprpp1.custom.collections;

/**
 * An array-indexed collection class. Duplicate elements are allowed. Storage of <code>null</code> references is not allowed.
 * 
 * @author fabjanvucina
 */
public class ArrayIndexedCollection extends Collection {
	
	/**
	 *  Current size of collection
	 */
	private int size;
	
	/**
	 * An array of object references whose length is determined by capacity argument in the constructor
	 */
	private Object[] elements;
	
	
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
	
	
	@Override
	public int size() {
		return this.size;
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
	

	/**
	 * @throws NullPointerException if <code>value == null</code>
	 */
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		if (this.size == this.elements.length) {
			this.reallocateElementsArray();
		}
		
		this.elements[this.size++] = value;
	}


	/**
	 * Method which removes the element at the specified index.
	 * @param index
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index should be in [0, " + (this.size - 1) + "] range.");
		
		for(int i=index; i<this.size-1; i++) {
			this.elements[i] = this.elements[i+1];
		}
		this.elements[this.size - 1] = null;
		
		this.size--;
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
		
		return false;
		
	}
	
	/**
	 * Method which inserts the passed object at specified index.
	 * @param value
	 * @param index
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size]</code> range
	 * @throws NullPointerException if <code>value == null</code>
	 */
	public void insert(Object value, int index) {
		if(index < 0 || index > this.size) throw new IndexOutOfBoundsException("Index should be in [0, " + this.size + "] range.");
	    if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
	    if (this.size == this.elements.length) {
			this.reallocateElementsArray();
		}
		
		for(int i=this.size; i>index; i--) {
			this.elements[i] = this.elements[i-1];
		}
		this.elements[index] = value;
		
		this.size++;
	}
	
	
	/**
	 * Method which returns the object at the specified index.
	 * @param index
	 * @return collection element
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
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
	
	
	/**
	 * @param value
	 * @return index of the first occurence if the element is found, -1 otherwise
	 */
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
	public void forEach(Processor processor) {
		for(Object element : this.elements) {
			if(element != null) {
				processor.process(element);
			}
		}
	}


	@Override
	public void clear() {
		for(int i=0; i<this.size; i++) {
			this.elements[i] = null;
		}
		 
		this.size = 0;
	}
	
}
