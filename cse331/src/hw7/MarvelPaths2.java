package hw7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import hw5.Graph;
import hw5.GraphNode;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;

/**
 * <b>MarvelPaths2</b> represents a <b>mutable</b> holder of a given graph or
 * creates a graph from a given valid tsv format file. It can do Dijkstra's
 * Algorithm on the graph and find the shortest path (more books connecting
 * each Marvel heroes in the path) to reach from the given node to another.
 * 
 * @author pinyiw
 */

public class MarvelPaths2 {
	
	
	// Abstraction Function:
	//
	//
	// Representation invariant for every MarvelPaths2 m:
	//	graph != null &&
	//	forall GraphNode node in graph, node != null &&
	//	forall GraphNode child of dest of an edge, child != null &&
	//	forall edge data ed in graph, ed != null.
	
	/**
	 * @param fileName the file name of the tsv data to be process.
	 * @throws MalformedDataException if the file of the given file name has
	 * 		   invalid tsv format.
	 * @effects Constructs a new MarvelPaths2 with the given file name and build
	 * 			a graph from that file.
	 */
//	public MarvelPaths2(String fileName) throws MalformedDataException {
//		MarvelPaths mp = new MarvelPaths(fileName);
//		Graph<String, String> mpGraph = mp.graph;
//		graph = new Graph<String, Double>();
//		for (GraphNode<String> node: mpGraph.nodes()) {
//			graph.addNode(node);
//			graph.addEdge(node, node, 0.0);
//		}
//		for (GraphNode<String> curNode: mpGraph.nodes()) {
//			for (GraphNode<String> child: mpGraph.childNode(curNode)) {
//				List<String> connections = mpGraph.getEdgeData(curNode, child);
//				Double weight = 1.0 / connections.size();
//				graph.addEdge(curNode, child, weight);
//			}
//		}
//		//checkRep();
//	}
	
	public static Graph<String, Double> loadGraph(String fileName)
					throws MalformedDataException {
		MarvelPaths mp = new MarvelPaths(fileName);
		Graph<String, String> mpGraph = mp.graph;
		Graph<String, Double> graph = new Graph<String, Double>();
		for (GraphNode<String> node: mpGraph.nodes()) {
			graph.addNode(node);
			graph.addEdge(node, node, 0.0);
		}
		for (GraphNode<String> curNode: mpGraph.nodes()) {
			for (GraphNode<String> child: mpGraph.childNode(curNode)) {
				List<String> connections = mpGraph.getEdgeData(curNode, child);
				Double weight = 1.0 / connections.size();
				graph.addEdge(curNode, child, weight);
			}
		}
		//checkRep();
		return graph;
	}
	
	/**
	 * 
	 * 
	 * @param <T>
	 * @param char1 the character we want to start on.
	 * @param char2 the character we want to find path from char1.
	 * @throws IllegalArgumenException if char1 == null || char2 == null ||
	 * 		   graph does not contains char1 or char2.
	 */
//	public void search(String char1, String char2) {
//		GraphNode<String> start = new GraphNode(char1);
//		GraphNode<String> dest = new GraphNode(char2);
//		// throw exception
//		if (char1 == null || char2 == null || !graph.contains(start) ||
//				!graph.contains(dest)) {
//			throw new IllegalArgumentException();
//		}
//		PriorityQueue workList = new PriorityQueue<GraphNode<String>>();
//	}
	
	public static <T> GraphNodePath<T> findPath(Graph<T, Double> graph,
														T char1, T char2) {
		GraphNode<T> start = new GraphNode<T>(char1);
		GraphNode<T> dest = new GraphNode<T>(char2);
		// throw exception
		if (char1 == null || char2 == null || !graph.contains(start) ||
				!graph.contains(dest)) {
			throw new IllegalArgumentException();
		}
		// initialization
		PriorityQueue<GraphNodePath<T>> workList = 
				new PriorityQueue<GraphNodePath<T>>(new GraphNodePathComparator<T>());
		Set<GraphNode<T>> visitedNodes = new HashSet<GraphNode<T>>();
		List<GraphNode<T>> startList = new ArrayList<GraphNode<T>>();
		startList.add(start);
		startList.add(start);
		workList.add(new GraphNodePath<T>(startList, 0.0));
		// start looping
		while (!workList.isEmpty()) {
			GraphNodePath<T> minPath = workList.remove();
			GraphNode<T> minDest = minPath.dest();
			if (minDest.equals(dest)) {
				return minPath;
			} else if (!visitedNodes.contains(minDest)) {
				Set<GraphNode<T>> 
				for (GraphNode<T> neighbor: )
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws MalformedDataException {
		// heroes.tsv || marvel.tsv || staffSuperheroes.tsv
		Graph<String, Double> graph = loadGraph("src/hw6/data/heroes.tsv");
		Scanner console = new Scanner(System.in);
		// ask user whether should it print out the whole graph.
		System.out.println("Do you want to print out the graph? (Y/N)");
		String print = console.nextLine();
		if (print.toUpperCase().startsWith("Y")) {
			// print out all the nodes.
			System.out.println("Characters:");
			Set<GraphNode<String>> chars = graph.nodes();
			for (GraphNode<String> node: chars) {
				System.out.println(node.getName());
			}
			System.out.println();
			// print out all the edges.
			for (GraphNode<String> node: chars) {
				System.out.println(node.getName() + ":");
				Set<GraphNode<String>> children = graph.childNode(node);
				for (GraphNode<String> child: children) {
					System.out.println("\t" + child.getName() + ":");
					List<Double> edges = graph.getEdgeData(node, child);
					for (int i = 0; i < edges.size(); i++) {
						System.out.println("\t\t" + edges.get(i));
					}
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Checks the representation invariant holds.
	 */
//	private void checkRep() {
//		assert (graph != null) : "graph equals to null";
//		for (GraphNode<String> node: graph.nodes()) {
//			assert (node != null) : "node equals to null";
//			for (GraphNode<String> child: graph.childNode(node)) {
//				assert (child != null) : "child node equals to null";
//				for (Double weight: graph.getEdgeData(node, child)) {
//					assert (weight != null) : "weight equals to null";
//				}
//			}
//		}
//	}
}
