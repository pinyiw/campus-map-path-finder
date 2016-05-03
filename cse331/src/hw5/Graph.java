package hw5;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <b>Graph</b> represents a <b>mutable</b> graph with GraphNode as its node
 * and Edge as its edge.
 * 
 * @author pinyiw
 */

public class Graph {
	
	/** The map that stores all the Edges. */
	private Map<GraphNode, List<Edge>> map;
	
	// Abstraction Function:
	// Graph, g, is a directed labeled multigraph that stores a list of
	// GraphNode and the Edge connect them.
	//
	// Representation invarian for every Graph g:
	//	map != null &&
	//	forall GraphNode node in map.keySet(), map.get(node) != null
	
	/**
	 * @effects Constructs a new Graph with no node or edge
	 */
	public Graph() {
		map = new HashMap<GraphNode, List<Edge>>();
		checkRep();
	}
	
	/**
	 * Check if this Graph contains the given node.
	 * 
	 * @param node the node to check if its in this Graph.
	 * @return true if node is in this Graph, otherwise, false.
	 */
	public boolean contains(GraphNode node) {
		return map.containsKey(node);
	}
	
	/**
	 * Check if the given two GraphNode are connected in this Graph.
	 * 
	 * @param start the start of the Edge to be checked.
	 * @param dest the destination of the Edge to be checked.
	 * @return true if start and dest is connected, otherwise, false.
	 * @throws IllegalArgumentexception if start is not in this Graph.
	 */
	public boolean isConnected(GraphNode start, GraphNode dest) {
		if (!this.contains(start)) {
			throw new IllegalArgumentException();
		}
		List<Edge> list = map.get(start);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDest().equals(dest)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add the given GraphNode to this Graph.
	 * 
	 * @param node the GraphNode to be added to this Graph.
	 * @return true if node is added to this Graph, false if node is already
	 * 		   in it.
	 */
	public boolean addNode(GraphNode node) {
		if (!this.contains(node)) {
			map.put(node, new LinkedList<Edge>());
			checkRep();
			return true;
		}
		return false;
	}
	
	/**
	 * Add an Edge with the given start and destination to this Graph.
	 * 
	 * @param start the start of the Edge to be added.
	 * @param dest the destination of the Edge to be added.
	 * @param data the data of the Edge to be added.
	 * @return true if start and dest was not connected, otherwise, false.
	 * @throws IllegalArgumentException if start or dest is not in this Graph
	 * 				|| data == null.
	 */
	public boolean addEdge(GraphNode start, GraphNode dest, String data) {
		if (!this.contains(start) || !this.contains(dest) || data == null) {
			throw new IllegalArgumentException();
		}
		boolean wasConnected = this.isConnected(start, dest);
		map.get(start).add(new Edge(data, dest));
		checkRep();
		return wasConnected;
	}
	
	/**
	 * Return the list of childNode of the given node 
	 * 
	 * @param node
	 * @return
	 */
	public List<GraphNode> childNode(GraphNode node) {
		return null;
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (map != null) : "map == null";
		for (GraphNode node: map.keySet()) {
			assert (map.get(node) != null) : "null list";
		}
	}
}