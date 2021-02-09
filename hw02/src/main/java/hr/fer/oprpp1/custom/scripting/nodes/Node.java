package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * A class which represents a graph node inside a structured document.
 * 
 * @author fabjanvucina
 */
public abstract class Node {
	
	/**
	 * Private children collection of the <code>Node</code> object.
	 */
	private ArrayIndexedCollection childrenCollection;
	
	/**
	 * Default constructor.
	 */
	public Node() {
		childrenCollection = null;
	}
	
	
	/**
	 * A method which adds a new child to the <code>Node</code> object.
	 * 
	 * @param child
	 * @throws NullPointerException if <code>child == null</code>
	 */
	public void addChildNode(Node child) {
		if(childrenCollection == null) {
			childrenCollection = new ArrayIndexedCollection();
		}
		
		childrenCollection.add(child);
	}
	
	
	/**
	 * @return number of children 
	 */
	public int numberOfChildren() {
		if(childrenCollection == null) {
			return 0;
		}
		
		return childrenCollection.size();
	}
	
	
	/**
	 * @param index Index of the wanted child.
	 * @return child at the specified index.
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of <code>[0, size-1]</code> range
	 */
	public Node getChild(int index) {
		return (Node)childrenCollection.get(index);
	}
	
	
	/**
	 * @return text representation of the node
	 */
	public abstract String getText();


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childrenCollection == null) ? 0 : childrenCollection.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (childrenCollection == null) {
			if (other.childrenCollection != null)
				return false;
		} else if (!childrenCollection.equals(other.childrenCollection))
			return false;
		return true;
	}	
}
