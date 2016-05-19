package hw5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b>Graph</b> represents a <b>mutable</b> labeled multigraph that stores
 * a list of GraphNode and the relation among them.
 * <p>
 * 
 * An edge, e, in a Graph can be notated by (a, b, D), where 'a' is the start
 * of the edge, 'b' is the destination of the edge, and D is the list of data
 * of different edges with same 'a' and 'b'.
 * 
 * An edge, e, in a Graph can also be notated by (a, B), where 'a' is the start
 * of the edge, B is the list of node that can be reachable from 'a'. 
 * 
 * @author pinyiw
 * 
 * @param <T> the type of the name stored in GraphNode of this Graph.
 * @param <D> the type of the edge data stored in this Graph.
 */

public class Graph<T, D> {
	
	/** The map that stores all the nodes and edges. */
	private Map<GraphNode<T>, Map<GraphNode<T>, List<D>>> map;
	
	// Abstraction Function:
	// Graph, g, is a directed labeled multigraph that stores a list of
	// GraphNode and connection among them.
	//
	// Representation invariant for every Graph g:
	//	map != null &&
	//	forall GraphNode node in map.keySet(), B of (node, B) != null &&
	//	forall b in B of (node, B), D of (a, b, D) != null &&
	//	forall d in D of (a, b, D), d != null
	
	/**
	 * @effects Constructs a new Graph with no node or edge
	 */
	public Graph() {
		map = new HashMap<GraphNode<T>, Map<GraphNode<T>, List<D>>>();
		//checkRep();
	}
	
	/**
	 * @param list the list of GraphNode to be constructed in this Graph.
	 * @effects Constructs a new Graph with the given list of GraphNode with no
	 * 			edge initially.
	 * @requires no repeated node in list
	 * @throws IllegalArgumentException if list == null.
	 */
	public Graph(List<GraphNode<T>> list) {
		this();
		if (list == null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i), new HashMap<GraphNode<T>, List<D>>());
		}
		//checkRep();
	}
	
	/**
	 * Check if this Graph contains the given node.
	 * 
	 * @param node the node to check if its in this Graph.
	 * @requires node != null.
	 * @return true if node is in this Graph, otherwise, false.
	 */
	public boolean contains(GraphNode<T> node) {
		return map.containsKey(node);
	}
	
	/**
	 * Returns the number of GraphNode in this Graph.
	 * 
	 * @return the number of GraphNode in this Graph.
	 */
	public int nodeSize() {
		return map.size();
	}
	
	/**
	 * Check is this Graph is empty.
	 * 
	 * @return true if there's no node in this Graph, otherwise, false.
	 */
	public boolean isEmpty() {
		return this.nodeSize() == 0;
	}
	
	/**
	 * Check if the given two GraphNode are connected in this Graph.
	 * 
	 * @param start the start of the edge to be checked.
	 * @param dest the destination of the edge to be checked.
	 * @return true if dest is reachable from start, otherwise, false.
	 * @throws IllegalArgumentexception if start == null || dest == null ||
	 * 		   start or dest is not in this Graph.
	 */
	public boolean isConnected(GraphNode<T> start, GraphNode<T> dest) {
		if (start == null || dest == null || !this.contains(start) ||
											!this.contains(dest)) {
			throw new IllegalArgumentException();
		}
		Set<GraphNode<T>> set = map.get(start).keySet();
		return set.contains(dest);
	}
	
	/**
	 * Add the given GraphNode to this Graph.
	 * 
	 * @param node the GraphNode to be added to this Graph.
	 * @requires node != null.
	 * @return true if node is added to this Graph, false if node is already
	 * 		   in it.
	 */
	public boolean addNode(GraphNode<T> node) {
		if (!this.contains(node)) {
			map.put(node, new HashMap<GraphNode<T>, List<D>>());
			//checkRep();
			return true;
		}
		//checkRep();
		return false;
	}
	
	/**
	 * Add an Edge with the given start and destination to this Graph.
	 * 
	 * @param start the start of the edge to be added.
	 * @param dest the destination of the edge to be added.
	 * @param data the data of the Edge to be added.
	 * @return true if dest was not reachable from start, otherwise, false.
	 * @throws IllegalArgumentException if start or dest is not in this Graph
	 * 				|| data == null.
	 */
	public boolean addEdge(GraphNode<T> start, GraphNode<T> dest, D data) {
		if (!this.contains(start) || !this.contains(dest) || data == null) {
			throw new IllegalArgumentException();
		}
		boolean wasConnected = this.isConnected(start, dest);
		if (!wasConnected) {
			map.get(start).put(dest, new LinkedList<D>());
		}
		map.get(start).get(dest).add(data);
		//checkRep();
		return wasConnected;
	}
	
	/**
	 * Gets the list string of data of edges from the given start node to dest
	 * node.
	 * 
	 * @param start the start of the edge to be check.
	 * @param dest the end of the edge to be check.
	 * @return null if dest is not reachable from start, otherwise, returns the
	 * 		   list of string data of edges that reach dest from start.
	 * @throws IllegalArgumentException if start or dest is not in this Graph.
	 */
	public List<D> getEdgeData(GraphNode<T> start, GraphNode<T> dest) {
		if (!this.contains(start) || !this.contains(dest)) {
			throw new IllegalArgumentException();
		}
		if (!map.get(start).containsKey(dest)) {
			return null;
		} else {
			return map.get(start).get(dest);
		}
	}
	
	/**
	 * Return a set of childNode of the given node.
	 * 
	 * @param node the parent node of the set of node returned
	 * @return the set of child node of 'node'.
	 * @throws IllegalArgumentException if node is not in this Graph.
	 */
	public Set<GraphNode<T>> childNode(GraphNode<T> node) {
		if (!this.contains(node)) {
			throw new IllegalArgumentException();
		}
		Set<GraphNode<T>> nodes = map.get(node).keySet();
		return nodes;
	}
	
	/**
	 * Return a set of all the nodes in this Graph.
	 * 
	 * @return all the nodes in this Graph as a set.
	 */
	public Set<GraphNode<T>> nodes() {
		Set<GraphNode<T>> temp = new HashSet<GraphNode<T>>();
		temp.addAll(map.keySet());
		return temp;
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (map != null) : "map == null";
		for (GraphNode<T> node: map.keySet()) {
			assert (map.get(node) != null) : "null dest map";
			Map<GraphNode<T>, List<D>> cur = map.get(node);
			for (GraphNode<T> dest: cur.keySet()) {
				assert(cur.get(dest) != null) : "null dest list";
				assert(cur.get(dest).size() != 0) : "empty dest list";
				List<D> list = cur.get(dest);
				for (int i = 0; i < list.size(); i++) {
					assert(list.get(i) != null) : "null edge data";
				}
			}
		}
	}
}