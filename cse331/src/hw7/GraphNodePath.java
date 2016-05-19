package hw7;

import java.util.ArrayList;
import java.util.List;

import hw5.GraphNode;

/**
 * <b>GraphNodePath</b> represents a <b>immutable</b> path that stores a list
 * of GraphNode as the path and the total weight of the path as its length.
 * <p>
 * 
 * @author pinyiw
 *
 * @param <T> the type of the name of GraphNode stored in this GraphNodePath.
 */

public class GraphNodePath<T> {
	
	/** The list of path this GraphNodePath stores. */
	private final List<GraphNode<T>> path;
	/** The length of this GraphNodePath. */
	private final Double length;
	
	// Abstraction Function:
	// GraphNodePath, gnp, is a path that stores a path of GraphNode and the
	// total weight of this path.
	// Examples of gnp: (a, b, c) : length == 6
	//					(b, c) : length == 2
	// where a, b and c are GraphNode.
	//
	// Representation invariant for every GraphNodePath gnp:
	//	path != null &&
	//	length != null &&
	//	forall GraphNode node in path, node != null.
	
	/**
	 * @param list the list of GraphNode to be stored in this GraphNodePath.
	 * @param length the total weight of this GraphNodePath.
	 * @effects Constructs a new GraphNodePath that stores the given list of 
	 * 			GraphNode and the total weight of the path.
	 * @throws IllegalArgumentException if list == null || length == null.
	 */
	public GraphNodePath(List<GraphNode<T>> list, Double length) {
		if (list == null || length == null) {
			throw new IllegalArgumentException();
		}
		path = new ArrayList<GraphNode<T>>();
		path.addAll(list);
		this.length = length;
		checkRep();
	}
	
	/**
	 * Return the count of node in this GraphNodePath.
	 * 
	 * @return the count of node in this GraphNodePath.
	 */
	public int nodeCount() {
		return path.size();
	}
	
	/**
	 * Return the total weight of this GraphNodePath.
	 * 
	 * @return the length of this GraphNodePath.
	 */
	public Double length() {
		return length;
	}
	
	/**
	 * Return the destination of this GraphNodePath.
	 * 
	 * @return the last element of this GraphNodePath.
	 */
	public GraphNode<T> dest() {
		return path.get(path.size() - 1);
	}
	
	/**
	 * Return the start of this GraphNodePath.
	 * 
	 * @return the first element of this GraphNodePath.
	 */
	public GraphNode<T> start() {
		return path.get(0);
	}
	
	/**
	 * Return a copy of list of GraphNode of this GraphNodePath.
	 * 
	 * @return list of GraphNode of this GraphNodePath.
	 */
	public List<GraphNode<T>> getPath() {
		List<GraphNode<T>> temp = new ArrayList<GraphNode<T>>();
		temp.addAll(path);
		return temp;
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	public void checkRep() {
		assert (path != null) : "path equals to null";
		assert (length != null) : "length equals to null";
		for (int i = 0; i < path.size(); i++) {
			assert (path.get(i) != null) : "node in path equals to null";
		}
	}
}
