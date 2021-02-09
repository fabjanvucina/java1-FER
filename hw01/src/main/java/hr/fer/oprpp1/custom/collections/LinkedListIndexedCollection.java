package hr.fer.oprpp1.custom.collections;


/**
 * A linked-list collection class. Duplicate elements are allowed. Storage of <code>null</code> references is not allowed.
 * 
 * @author fabjanvucina
 */
public class LinkedListIndexedCollection extends Collection {
	
	
	/**
	 * Linked-list node class.
	 * 
	 * @author fabjanvucina
	 *
	*/
	private static class ListNode {

		/**
		 * reference to previous list node
		 */
		private ListNode previous;
		
		/**
		 * reference to next list node
		 */
		private ListNode next;
		
		/**
		 * value of list node
		 */
		private Object value;	
		
		/**
		 * Public constructor.
		 * @param value
		 */
		public ListNode(Object value) {
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
	private ListNode first;
	
	
	/**
	 * reference to the last node of the linked list
	 */
	private ListNode last;
	
	
	/**
	 * Default constructor which initializes the first and last list nodes to <code>null</code>.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	
	
	/**
	 * Public constructor which copies elements from the passed collection into the newly created collection.
	 * @param other
	 * @throws NullPointerException if <code>other == null</code>
	 */
	public LinkedListIndexedCollection(Collection other) {		
		this();
		
		if(other == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		this.addAll(other);
	}


	@Override
	public int size() {
		return this.size;
	}

	
	/** 
	 * @throws NullPointerException if <code>value == null</code>
	 */
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		ListNode ln = new ListNode(value);
		
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
		
	}
	

	/**
	 * Method which removes the element at the specified index.
	 * @param index
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index should be in [0, " + (this.size - 1) + "] range.");
		
		//get to the element that needs to be removed
		//we will detach ln from the list by removing connections to him from the other nodes
		ListNode ln = this.first;
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
	}
	
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	@Override
	public boolean remove(Object value) {
		if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
		ListNode ln= this.first;
		for(int i=0; ln != null; ln=ln.next, i++) {
			if(ln.value.equals(value)) {
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
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException("Index should be in [0, " + this.size + "] range.");
	    if(value == null) throw new NullPointerException("You passed null as a reference to an object.");
		
	    //we will insert a new node by defining connections to the nodes we are inserting the new node between
	    ListNode ln = new ListNode(value);
	    
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
	    	ListNode node = this.first;
			for(int i = 0; i<position; node=node.next, i++);
			
			//left connection
			node.previous.next = ln;
			ln.previous = node.previous;
			
			//right connection
			node.previous = ln;
			ln.next = node;
	    }
	    
	    
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
		
		ListNode ln;
		
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
		
		for(ListNode ln = this.first; ln != null; ln=ln.next) {
			if(ln.value.equals(value)) {
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
		
		ListNode ln = this.first;
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
		
		ListNode ln = this.first;
		for(int i=0; i<this.size; i++, ln=ln.next) {
			array[i] = ln.value;
		}
		
		return array;
	}


	@Override
	public void forEach(Processor processor) {
		for(ListNode ln=this.first; ln != null; ln=ln.next) {
			processor.process(ln.value);
		}
	}


	
	@Override
	public void clear() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
}
