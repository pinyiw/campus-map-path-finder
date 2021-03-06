package hw6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import hw5.Graph;
import hw5.GraphNode;
import hw6.MarvelParser.MalformedDataException;


/**
 * <b>MarvelPaths</b> represents a <b>mutable</b> holder of a given graph or
 * creates a graph from a given valid tsv format file. It can do BFS search
 * on the graph and find the shortest path to reach from one given node to
 * another.
 * 
 * @author pinyiw
 */

public class MarvelPaths {
	
	/** The graph that stores the given data. */
	public Graph<String, String> graph;
	
	// Abstraction Function:
	// MarvelPaths, mp, is a graph holder that holds a graph from a given graph
	// or creates a graph from a given valid tsv format file, and it can do BFS
	// search on the graph it constructed to find out the shortest path to
	// reach from one node to another.
	//
	// Representation invariant for every MarvelPaths m:
	//	graph != null &&
	//	forall GraphNode node in graph, node != null &&
	//	forall GraphNode child of dest of an edge, child != null &&
	//	forall edge data ed in graph, ed != null
	
	/** 
	 * @param fileName the file name of the tsv data to be process.
	 * @throws MalformedDataException if the file of the given file name has
	 * 		   invalid tsv format.
	 * @effects Constructs a new MarvelPaths with the given file name and build
	 * 			a graph from that file.
	 */
	public MarvelPaths(String fileName) throws MalformedDataException {
		Set<String> characters = new HashSet<String>();
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		MarvelParser.parseData(fileName, characters, books);
		List<GraphNode<String>> chars = new ArrayList<GraphNode<String>>();
		// Create and add GraphNodes to a list of GraphNode.
		for (String character: characters) {
			chars.add(new GraphNode<String>(character));
		}
		// Initialize graph with the list of GraphNode and add edges.
		graph = new Graph<String, String>(chars);
		for (String book: books.keySet()) {
			List<String> curChars = books.get(book);
			for (int i = 0; i < curChars.size() - 1; i++) {
				GraphNode<String> first = new GraphNode<String>(curChars.get(i));
				for (int j = i + 1; j < curChars.size(); j++) {
					GraphNode<String> second = new GraphNode<String>(curChars.get(j));
					// Add two-direction edges.
					graph.addEdge(first, second, book);
					graph.addEdge(second, first, book);
				}
			}
		}
		//checkRep();
	}
	
	/**
	 * @param graph the graph we want to find paths with.
	 * @throws IllegalArgumentException if graph == null.
	 * @effects Constructs a new MarvelPath with the given graph.
	 * @requires graph has no null node or null edge data.
	 */
	public MarvelPaths(Graph<String, String> graph) {
		if (graph == null) {
			throw new IllegalArgumentException();
		}
		this.graph = graph;
	}
	
	/**
	 * Find the shortest path between two characters, returning the characters 
	 * visited and books that connected those characters in the path.
	 * 
	 * @param char1 the character we want to start on.
	 * @param char2 the character we want to find path from char1.
	 * @throws IllegalArgumenException if char1 == null || char2 == null ||
	 * 		   graph does not contains char1 or char2.
	 * @return null if connection between char1 and char2 was not found, 
	 * 		   an empty list of string if char1 == char2,
	 * 		   otherwise, return a list of string with each characters followed
	 * 		   by the book that connect current character to the previous one.
	 * 		   If more than one path are available, it would return the
	 * 		   shortest path with lexicographically first character at each
	 * 		   step in the path, and lexicographically lowest title book. 
	 * 		   For example, char1 to charA via book1
	 * 						charA to charB via book2
	 * 						charB to char2 via book3,
	 * 		   It would return: [charA, book1, charB, book2, char2, book3].
	 */
	public List<String> search(String char1, String char2) {
		GraphNode<String> start = new GraphNode<String>(char1);
		GraphNode<String> dest = new GraphNode<String>(char2);
		// throw exception
		if (char1 == null || char2 == null || !graph.contains(start)
				|| !graph.contains(dest)) {
			throw new IllegalArgumentException();
		}
		// initialization of BFS
		Queue<GraphNode<String>> workList = new LinkedList<GraphNode<String>>();
		Map<GraphNode<String>, List<GraphNode<String>>> paths = 
							new HashMap<GraphNode<String>, List<GraphNode<String>>>();
		
		List<GraphNode<String>> resultPath = new ArrayList<GraphNode<String>>();
		workList.add(start);
		paths.put(start, new ArrayList<GraphNode<String>>());
		// start the loop part of BFS
		while (!workList.isEmpty()) {
			GraphNode<String> cur = workList.remove();
			if (cur.equals(dest)) {
				resultPath = paths.get(cur);
				// add the start node at the start of the list
				resultPath.add(0, start);
				workList.clear();
			} else {
				Set<GraphNode<String>> neigborSet = graph.childNode(cur);
				List<GraphNode<String>> neighbors = new ArrayList<GraphNode<String>>();
				neighbors.addAll(neigborSet);
				Collections.sort(neighbors, new StringGraphNodeComparator());
				for (GraphNode<String> neighbor: neighbors) {
					if (!paths.containsKey(neighbor)) {
						List<GraphNode<String>> curPath = new ArrayList<GraphNode<String>>();
						curPath.addAll(paths.get(cur));
						curPath.add(neighbor);
						paths.put(neighbor, curPath);
						workList.add(neighbor);
					}
				}
			}
		}
		return processPathToReadableList(resultPath);
	}
	
	/**
	 * Process the given list of GraphNode to a readable list of string.
	 * 
	 * @param path the list of GraphNode to be processed.
	 * @return null if path is empty,
	 * 		   an empty list of string if the first and last GraphNode equals,
	 * 		   otherwise, return a list of string with each characters followed
	 * 		   by the book that connect current character to the previous one.
	 * 		   If more than one path are available, it would return the
	 * 		   shortest path with lexicographically first character at each
	 * 		   step in the path, and lexicographically lowest title book. 
	 * 		   For example, char1 to charA via book1
	 * 						charA to charB via book2
	 * 						charB to char2 via book3,
	 * 		   It would return: [charA, book1, charB, book2, char2, book3].
	 */
	private List<String> processPathToReadableList(List<GraphNode<String>> path) {
		if (path.isEmpty()) {
			// return null if no path found.
			return null;
		} else if (path.get(0).equals(path.get(path.size() - 1))) {
			// return an empty list if start and dest are the same node.
			return new ArrayList<String>();
		} else {
			// return a format list with each node follow by the edge.
			List<String> result = new ArrayList<String>();
			for (int i = 0; i < path.size() - 1; i++) {
				result.add(path.get(i + 1).getName());
				List<String> edges = graph.getEdgeData(path.get(i), 
														path.get(i + 1));
				Collections.sort(edges);
				result.add(edges.get(0));
			}
			return result;
		}
	}
	
	/** 
	 * This main method construct a new MarvelPaths and interact with users to
	 * print out the graph and find the shortest path from one given node to
	 * another.
	 */
	public static void main(String[] args) throws MalformedDataException {
		MarvelPaths mp = new MarvelPaths("src/hw6/data/marvel.tsv");
		Scanner console = new Scanner(System.in);
		// ask user whether should it print out the whole graph.
		System.out.print("Do you want to print out the graph? (Y/N)");
		String print = console.nextLine();
		if (print.toUpperCase().startsWith("Y")) {
			// print out all the nodes.
			System.out.println("Characters:");
			Set<GraphNode<String>> chars = mp.graph.nodes();
			for (GraphNode<String> node: chars) {
				System.out.println(node.getName());
			}
			System.out.println();
			// print out all the edges.
			for (GraphNode<String> node: chars) {
				System.out.println(node.getName() + ":");
				Set<GraphNode<String>> children = mp.graph.childNode(node);
				for (GraphNode<String> child: children) {
					System.out.println("\t" + child.getName() + ":");
					List<String> edges = mp.graph.getEdgeData(node, child);
					for (int i = 0; i < edges.size(); i++) {
						System.out.println("\t\t" + edges.get(i));
					}
				}
			}
			System.out.println();
		}
		// start looping and ask what nodes connection user is interested in.
		boolean again = true;
		while (again) {
			System.out.println("What connection of characters are you interested in?");
			System.out.print("Character one: ");
			String char1 = console.nextLine().trim();
			System.out.print("Character two: ");
			String char2 = console.nextLine().trim();
			
			List<String> path = mp.search(char1, char2);
			// print out the path it returns
			System.out.println(path);
			
			System.out.print("Want to search more? (Y/N) ");
			String ans = console.nextLine();
			if (ans.toUpperCase().startsWith("N")) {
				again = false;
				System.out.println("Have a nice day! :D");
			}
			System.out.println();
		}
		console.close();
	}
	
	/**
	 * Checks the representation invariant holds.
	 */
	private void checkRep() {
		assert (graph != null) : "graph equals to null";
		Set<GraphNode<String>> nodes = graph.nodes();
		for (GraphNode<String> node: nodes) {
			assert (node != null) : "node equals to null";
			Set<GraphNode<String>> children = graph.childNode(node);
			for (GraphNode<String> child: children) {
				assert (child != null) : "child equals to null";
				List<String> edges = graph.getEdgeData(node, child);
				for (int i = 0; i < edges.size(); i++) {
					assert (edges.get(i) != null) : "edge equals to null";
				}
			}
		}
	}
}
