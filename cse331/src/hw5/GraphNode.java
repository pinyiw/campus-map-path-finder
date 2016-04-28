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

public class GraphNode {
	
	/** Name of this node. */
	private final String name;
	
	// Abstraction Function:
	// GraphNode, n, is a node of graph that stores its own name.
	
	// Representation invariant for every GraphNode n:
	// 	name != null &&
	// 	name.length() != 0
	
	/** 
	 * @param name the name of the GraphNode to be constructed.
	 * @requires name != null && name is not empty string.
	 * @effects Constructs a new GraphNode with name = name.
	 */
	public GraphNode(String name) {
		this.name = name;
		checkRep();
	}
	
	/**	
	 * Gets the name of this GraphNode.
	 * 
	 * @return the name of this GraphNode.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
	 */
	private void checkRep() {
		assert (name != null) : "name == null";
		assert (name.length() != 0) : "name is empty string";
	}
	
}
