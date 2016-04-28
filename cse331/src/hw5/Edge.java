package hw5;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
	
	/** Starting node of this edge. */
	private final GraphNode start;
	
	/** Ending node of this edge. */
	private final GraphNode end;
	
	/** Data of this node. */
	private final String data;
	
	// Abstraction Function:
	// Edge, e, is an edge that represent end is reachable from start and
	// stores a string data of the edge.
	
	// Representation invariant for every Edge e:
	//	start != null &&
	// 	end != null &&
	//	data != null
	
	public Edge(String data, GraphNode start, GraphNode end) {
		this.start = start;
		this.end = end;
		this.data = data;
	}
	
	public GraphNode getStart() {
		throw new NotImplementedException();
	}
}
