package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * A linked-list collection class. Duplicate elements are allowed. Storage of <code>null</code> references is not allowed.
 * 
 * @author fabjanvucina
 * @param <E> collection element type
 */
public class LinkedListIndexedCollection<E> implements List<E> {
	
	
	/**
	 * Linked-list node class.
	 * 
	 * @author fabjanvucina
	*/
	private static class ListNode<E> {

		/**
		 * reference to previous list node
		 */
		private ListNode<E> previous;
		
		/**
		 * reference to next list node
		 */
		private ListNode<E> next;
		
		/**
		 * value of list node
		 */
		private E value;	
		
		/**
		 * Public constructor.
		 * @param value
		 */
		public ListNode(E value) {
			this.previous = null;
			this.next = null;
			this.value = value;
		}
	}
	
	
	/**
	 * number of stored elements
	 */
	private int size;
	
	
	/**
	 * reference to the first node of the linked list
	 */
	private ListNode<E> first;
	
	
	/**
	 * reference to the last node of the linked list
	 */
	private ListNode<E> last;
	
	/**
	 * A counter which keeps track on the number of structural modifications on this object.
	 */
	private long modificationCount;
	
	
	/**
	 * Default constructor which initializes the first and last list nodes to <code>null</code>.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = null;
		this.last = null;
		this.modificationCount = 0;
	}
	
	
	/**
	 * Public constructor which copies elements from the passed collection into the newly created collection.
	 * @param other
	 * @throws NullPointerException if <code>other == null</code>
	 */
	public LinkedListIndexedCollection(Collection<? extends E> other) {		
		this();
		
		if(other == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		this.addAll(other);
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
		
		ListNode<E> ln = new ListNode<>(value);
		
		//if list is empty
		if(this.size == 0) {
			this.first = ln;
			this.last = ln;
		}
		
		else {
			//creating connection left of ln
			ln.previous = this.last;
			this.last.next = ln;
			
			this.last = ln;
		}
		
		
		this.size++;
		
		modificationCount++;
		
	}
	

	/**
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
	@Override
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index should be in [0, " + (this.size - 1) + "] range.");
		
		//get to the element that needs to be removed
		//we will detach ln from the list by removing connections to him from the other nodes
		ListNode<E> ln = this.first;
		for(int i = 0; i<index; ln=ln.next, i++);
		
		//removing first element
		if(index == 0) {
			this.first = ln.next;
			ln.next.previous = null;
		}
		
		//removing last element
		else if(index == this.size - 1) {
			this.last = ln.previous;
			ln.previous.next = null;
		}
		
		//removing element in the middle of the list
		else {
			ln.previous.next = ln.next;
			ln.next.previous = ln.previous;
		}
		
		
		this.size--;
		
		modificationCount--;
	}
	
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	@Override
	public boolean remove(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		ListNode<E> ln= this.first;
		for(int i=0; ln != null; ln=ln.next, i++) {
			if(ln.value.equals(value)) {
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
		
	    //we will insert a new node by defining connections to the nodes we are inserting the new node between
	    ListNode<E> ln = new ListNode<>(value);
	    
	    //if list is empty
	    if(this.size == 0) {
	    	this.first = ln;
			this.last = ln;
	    }
	    
	    //insert before the first element of non-empty list
	    else if (position == 0) {
	    	//right connection
	    	ln.next = this.first;
	    	this.first.previous = ln;
	    	
	    	this.first = ln;
	    }
	    
	    //insert before the last element of non-empty list
	    else if (position == this.size - 1) {
	    	//left connection
	    	ln.previous = this.last.previous;
	    	this.last.previous.next = ln;
	    	
	    	//right connection
	    	ln.next = this.last;
	    	this.last.previous = ln;	    	
	    }
	    
	    //insert after the last element of non-empty list
	    else if (position == this.size) {
	    	//left connection
	    	ln.previous = this.last;
	    	this.last.next = ln;
	    	
	    	this.last = ln;
	    	
	    }
	    
	    //insert in middle of list
	    else {
	    	//node will have to shift right
	    	ListNode<E> node = this.first;
			for(int i = 0; i<position; node=node.next, i++);
			
			//left connection
			node.previous.next = ln;
			ln.previous = node.previous;
			
			//right connection
			node.previous = ln;
			ln.next = node;
	    }
	    
	    
		this.size++;
		
		modificationCount++;
	}
	
	
	/**
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index should be in [0, " + (this.size - 1) + "] range.");
		
		ListNode<E> ln;
		
		if(index < this.size / 2) {
			ln = this.first;
			for(int i = 0; i<index; ln=ln.next, i++);
		}
		else {
			ln = this.last;
			for(int i = this.size - 1; i>index; ln=ln.previous, i--);
		}
		
		
		return ln.value;
		
	}
	
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	@Override
	public boolean contains(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		for(ListNode<E> ln = this.first; ln != null; ln=ln.next) {
			if(ln.value.equals(value)) {
				return true;
			}
		}
		
		return false;
	}

	
	@Override
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		ListNode<E> ln = this.first;
		for(int i = 0; i<this.size; ln=ln.next, i++) {
			if(ln.value.equals(value)) {
				return i;
			}
		}
		
		return -1;
	}
	

	@Override
	public Object[] toArray() {
		var array = new Object[this.size];
		
		ListNode<E> ln = this.first;
		for(int i=0; i<this.size; i++, ln=ln.next) {
			array[i] = ln.value;
		}
		
		return array;
	}

	
	@Override
	public void clear() {
		this.first = null;
		this.last = null;
		this.size = 0;
		
		modificationCount++;
	}


	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new LinkedListElementsGetter<E>(this);
	}
	
	
	/**
	 * Implementation of <code>ElementsGetter</code> interface.
	 *  
	 * @author fabjanvucina
	 *
	 */
	private static class LinkedListElementsGetter<E> implements ElementsGetter<E> {
		
		/**
		 * Private variable which keeps the reference of the collection for which we are creating n instance of an <code>LinkedListElementsGetter</code>.
		 */
		private LinkedListIndexedCollection<E> collection;
		
		/**
		 * Index of element that is to be returned next.
		 */
		private ListNode<E> currentNode;
		
		/**
		 * Number of unreturned elements in the collection.
		 */
		private int noOfRemainingElements;
		
		/**
		 * Private variable which remembers the original number of modifications on the collection object at the moment of the <code>LinkedListElementsGetter</code> creation.
		 */
		private long savedModificationCount;
		
		
		/**
		 * Public constructor.
		 * @param collection
		 */
		private LinkedListElementsGetter(LinkedListIndexedCollection<E> collection) {
			this.collection = collection;
			currentNode = collection.first;
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
			
			E nextElement = currentNode.value;
			currentNode = currentNode.next;
			
			return nextElement;
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
}
