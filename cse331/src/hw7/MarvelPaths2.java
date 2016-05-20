package hw7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import hw5.Graph;
import hw5.GraphNode;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;

public class MarvelPaths2 {
	
	// This is not an ADT.
	// Abstraction Function and Representation invariant would normally be here.
	
	/**
	 * Constructs and returns a Graph from the given file name that stores
	 * all heroes and inverse number of books from the file.
	 * 
	 * @param fileName the file name of the tsv data to be process.
	 * @throws MalformedDataException if the file of the given file name has
	 * 		   invalid tsv format.
	 * @return a Graph that stores all heroes as its GraphNode and the inverse
	 * 		   number of books as the edge data connecting two nodes.
	 */
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
		return graph;
	}
	
	/**
	 * Find the path between the two given characters that has least total
	 * weight.
	 * 
	 * @param graph the graph to be searched
	 * @param char1 the character to start on.
	 * @param char2 the character to find path from char1.
	 * @param <T> the type of that the GraphNodes store in graph
	 * @throws IllegalArgumenException if char1 == null || char2 == null ||
	 * 		   graph does not contains char1 or char2.
	 * @return null if char2 is not reachable from char1 in the given graph,
	 * 		   otherwise, a GraphNodePath that stores a list of GraphNode as
	 * 		   the path, p, and the length of p.
	 * 		   p is a GraphNodePath that contains a list of GraphNode that is
	 * 		   the shortest path (least total weight) that can reach char2 from
	 * 		   char1.
	 */
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
		workList.add(new GraphNodePath<T>(startList, 0.0));
		// start looping
		while (!workList.isEmpty()) {
			GraphNodePath<T> minPath = workList.remove();
			GraphNode<T> minDest = minPath.dest();
			if (minDest.equals(dest)) {
				return minPath;
			} else if (!visitedNodes.contains(minDest)) {
				for (GraphNode<T> child: graph.childNode(minDest)) {
					if (!visitedNodes.contains(child)) {
						List<GraphNode<T>> minList = minPath.getPath();
						minList.add(child);
						List<Double> curWeight = graph.getEdgeData(minDest, child);
						Collections.sort(curWeight);
						Double minWeight = minPath.length() + curWeight.get(0);
						GraphNodePath<T> newPath = 
									new GraphNodePath<T>(minList, minWeight);
						workList.add(newPath);
					}
				}
				visitedNodes.add(minDest);
			}
		}
		return null;
	}
	
	/**
	 * This main method interact with user to print out the graph and find the
	 * shortest path from one given node to another.
	 */
	public static void main(String[] args) throws MalformedDataException {
		Scanner console = new Scanner(System.in);
		// heroes.tsv || marvel.tsv || staffSuperheroes.tsv
		System.out.println("Which data file do you want to load?");
		System.out.println("1. heroes.tsv  2. marvel.tsv  3. staffSuperheroes.tsv");
		String choice = console.nextLine().trim();
		String file = "staffSuperheroes.tsv";
		if (choice.equals("1")) {
			file = "heroes.tsv";
		} else if (choice.equals("2")) {
			file = "marvel.tsv";
		}
		Graph<String, Double> graph = loadGraph("src/hw7/data/" + file);
		// ask user whether should it print out the whole graph.
		System.out.print("Do you want to print out the graph? (Y/N)");
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
			int edgeCount = 0;
			for (GraphNode<String> node: chars) {
				System.out.println(node.getName() + ":");
				Set<GraphNode<String>> children = graph.childNode(node);
				for (GraphNode<String> child: children) {
					System.out.println("\t" + child.getName() + ":");
					List<Double> edges = graph.getEdgeData(node, child);
					for (int i = 0; i < edges.size(); i++) {
						edgeCount++;
						System.out.println("\t\t" + edges.get(i));
					}
				}
			}
			System.out.println("Edge Count: " + edgeCount);
			System.out.println("Node Count: " + graph.nodeSize());
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
			
			GraphNodePath<String> gnp = findPath(graph, char1, char2);
			if (gnp == null) {
				System.out.println("Path not found! :(");
			} else {
				List<GraphNode<String>> path = gnp.getPath();
				System.out.print("[" + path.get(0).getName());
				for (int i = 1; i < path.size(); i++) {
					System.out.print(", " + path.get(i).getName());
				}
				System.out.println("]");
				System.out.println("Length: " + gnp.length());
			}
			
			System.out.print("Want to search more? (Y/N)");
			String ans = console.nextLine();
			if (ans.toUpperCase().startsWith("N")) {
				again = false;
				System.out.println("Have a nice day! :D");
			}
			System.out.println();
		}
		console.close();
	}
	
}
