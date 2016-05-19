package hw5;

/**
 * <b>GraphNode</b> represents an <b>immutable</b> node of a graph.
 * 
 * <p>
 * It stores a String name of itself.
 * 
 * @author pinyiw
 *
 */

public class GraphNode<T> {
	
	/** Name of this node. */
	private final T name;
	
	// Abstraction Function:
	// GraphNode, n, is a node of graph that stores its own name.
	//
	// Representation invariant for every GraphNode n:
	// 	name != null
	
	/** 
	 * @param name the name of this GraphNode to be constructed.
	 * @requires name != null.
	 * @effects Constructs a new GraphNode with name = name.
	 * @throws IllegalArgumentException if name == null.
	 */
	public GraphNode(T name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		checkRep();
	}
	
	/**	
	 * Gets the name of this GraphNode.
	 * 
	 * @return the name of this GraphNode.
	 */
	public T getName() {
		return this.name;
	}
	
	/**
	 * Check if this GraphNode has the same name as other.
	 * 
	 * @param other the object to be checked
	 * @return true if other is GraphNode and has same name as this GraphNode,
	 * 		   otherwise, false.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GraphNode)) {
			return false;
		} else {
			return this.getName().equals(((GraphNode)other).getName());
		}
	}
	
	/**
	 * Returns the hash code of this GraphNode.
	 * 
	 * @return the hashCode of this GraphNode.
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (name != null) : "name == null";
	}
	
}
