package hw5;

/**
 * <b>Edge</b> is an <b>immutable</b> representation of a directed edge
 * connecting two GraphNode with data of the edge.
 * <p>
 * 
 * An Edge, e, can be notated by (A, B, d), where A is the name of the
 * starting node, B is the name of the ending node, and d is the data of e.
 * <p>
 * 
 * (A, B, ""), (B, C, ""), and (B, A, "") are all valid Edges, corresponding 
 * to the edges "B is reachable from A", "C is reachable from B" and "A is
 * reachable from B."
 * 
 * @author pinyiw
 */

public class Edge {
	
	/** Ending node of this edge. */
	private final GraphNode dest;
	
	/** Data of this node. */
	private final String data;
	
	// Abstraction Function:
	// Edge, e, is an edge that represent dest is reachable from start and
	// stores a string data of the edge.
	
	// Representation invariant for every Edge e:
	//	dest != null &&
	//	data != null
	
	/**
	 * @param data the data this Edge stores.
	 * @param dest the destination of this Edge.
	 * @requires dest != null && data != null.
	 * @effects Constructs a new Edge which stores data and has destination
	 * 			dest.
	 * @throws IllegalArgumentException if dest == null || data == null.
	 */
	public Edge(String data, GraphNode dest) {
		if (dest == null || data == null) {
			throw new IllegalArgumentException();
		}
		this.dest = dest;
		this.data = data;
		checkRep();
	}
	
	/**
	 * Gets the data stored in this Edge.
	 * 
	 * @return the data stored in this Edge.
	 */
	public String getData() {
		return this.data;
	}
	
	/**
	 * Gets the destination node of this Edge.
	 * 
	 * @return the destination node of this Edge.
	 */
	public GraphNode getDest() {
		return this.dest;
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (dest != null) : "dest == null";
		assert (data != null) : "data == null";
	}
}
