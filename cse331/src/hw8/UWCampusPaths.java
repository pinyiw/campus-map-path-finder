package hw8;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import hw5.Graph;
import hw5.GraphNode;
import hw7.GraphNodePath;
import hw7.MarvelPaths2;
import hw8.DataParser.MalformedDataException;
import javafx.util.Pair;

/**
 * <b>UWCampusPaths</b> represents a manager and parser of two given file path
 * that stores the information of buildings and paths.
 * 
 * @author pinyiw
 */
public class UWCampusPaths {
	
	/** The graph that stores all the location and distance betweeen them. */
	private final Graph<Pair<String, String>, Double> graph;
	/** The map that stores all the information of buildings. */
	private final Map<String, Location> buildings;
	
	// Abstraction Function:
	// buildings stores the information of all buildings and graph stores
	// all the path and distance between locations.
	//
	// Representation invariant:
	//	buildings != null &&
	//	forall String  shortName in buildings.keySet(), Location l != null &&
	// 	graph != null &&
	//	forall GraphNode node in graph, node != null &&
	//	forall GraphNode child of dest of an edge, child != null &&
	//	forall edge data ed in graph, ed != null
	
	public UWCampusPaths(String buildingsFileName, String pathsFileName)
						throws MalformedDataException {
		buildings = loadBuildings(buildingsFileName);
		graph = loadGraph(pathsFileName);
		//checkRep();
	}
	
	/**
	 * Constructs and returns a TreeMap with the given file name that stores
	 * building information.
	 * 
	 * @requires buildingFileName is a valid file path.
	 * @param buildingsFileName the file that will be read.
	 * @modify this.
	 * @return a TreeMap that has key as the short name of buildings and value
	 * 		   as a Location that stores information about the building.
	 * @throws MalformedDataException if the file of the given file name has
	 * 		   invalid format. Each line of the input file should contains a
	 * 		   short name, a long name, an x and a y of a building, separated
	 * 		   by a tab character.
	 */
	private Map<String, Location> loadBuildings(String buildingsFileName)
			throws MalformedDataException {
		Map<String, Location> buildings = new TreeMap<String, Location>();
		DataParser.parseBuildingsData(buildingsFileName, buildings);
		return buildings;
	}
	
	/**
	 * Constructs and returns a Graph with the given file name that stores
	 * locations of UW campus.
	 * 
	 * @requires pathsFileName is valid file path.
	 * @param pathsFileName the file name to be process.
	 * @modify this.
	 * @return a Graph that stores the x and y coordinate as a Pair as the type
	 * 		   of GraphNode, and stores the distance as Double as edge data.
	 * @throws MalformedDataException if the file is not well-formed.
	 * 		   For each endpoint of  a path segment, there is a line in the
	 * 		   file listing the pixel coordinates of that point, followed by a
	 * 		   tab-indented line for each endpoint to which it is connected
	 * 		   with a path segment.
	 * 		   Each indented line lists the coordinates of the other endpoint
	 * 		   and the distance of the segment between them in feet.
	 */
	private Graph<Pair<String, String>, Double> loadGraph(String pathsFileName) 
			throws MalformedDataException {
		Map<Pair<String, String>, List<Pair<Pair<String, String>, Double>>> paths =
				new HashMap<Pair<String, String>, List<Pair<Pair<String, String>, Double>>>();
		DataParser.parsePathsData(pathsFileName, paths);
		Graph<Pair<String, String>, Double> graph = new Graph<Pair<String, String>, Double>();
		
		for (Pair<String, String> xy: paths.keySet()) {
			GraphNode<Pair<String, String>> cur = new GraphNode<Pair<String, String>>(xy);
			graph.addNode(cur);
			List<Pair<Pair<String, String>, Double>> curList = paths.get(xy);
			for (int i = 0; i < curList.size(); i++) {
				Pair<String, String> xy2 = curList.get(i).getKey();
				Double dist = curList.get(i).getValue();
				GraphNode<Pair<String, String>> dest = new GraphNode<Pair<String,String>>(xy2);
				if (!graph.contains(dest)) {
					graph.addNode(dest);
				}
				graph.addEdge(cur, dest, dist);
			}
		}
		return graph;
	}
	
	/**
	 * Returns the path from the given coordinate of starting position to the
	 * coordinates of destination.
	 * 
	 * @param start the location to start on. 
	 * @param dest the location to find path from start.
	 * @return null if dest is not reachable from start,
	 * 		   otherwise, a GraphNodePath that stores a list of GraphNode as
	 * 		   the path, p, and the length of p.
	 * 		   The list of GraphNode in p includes start and dest as the first
	 * 		   and last elements in the list respectively.
	 */
	public GraphNodePath<Pair<String, String>> findPath(Pair<String, String> start,
													Pair<String, String> dest) {
		return MarvelPaths2.findPath(graph, start, dest);
	}
	
	/**
	 * Returns the distance from the given start to the given destination.
	 * 
	 * @param start the location to start on.
	 * @param dest the destination to find distance from start.
	 * @return the shortest distance from position start to position dest.
	 */
	public Double getDistance(Pair<String, String> start, Pair<String, String> dest) {
		GraphNode<Pair<String, String>> startNode = 
							new GraphNode<Pair<String, String>>(start);
		GraphNode<Pair<String, String>> destNode =
							new GraphNode<Pair<String, String>>(dest);
		List<Double> distList = graph.getEdgeData(startNode, destNode);
		Collections.sort(distList);
		return distList.get(0);
	}
	
	/**
	 * Returns the information of buildings as a Map.
	 * 
	 * @return a map that stores information of buildings, with key, the
	 * 		   short name of buildings, and value, Location that stores the
	 * 		   information of the building.
	 * 		   Buildings are ordered in alphabetically order by their short
	 * 		   names.
	 */
	public Map<String, Location> getBuildingsInfo() {
		return buildings;
	}
	
	/**
	 * Checks the representation invariant of this.
	 */
	private void checkRep() {
		assert (buildings != null) : "buildings is null";
		assert (graph != null) : "graph is null";
		for (String shortName: buildings.keySet()) {
			assert (buildings.get(shortName) != null) : "location is null";
		}
		Set<GraphNode<Pair<String, String>>> nodes = graph.nodes();
		for (GraphNode<Pair<String, String>> node: nodes) {
			assert (node != null) : "node equals to null";
			Set<GraphNode<Pair<String, String>>> children = graph.childNode(node);
			for (GraphNode<Pair<String, String>> child: children) {
				assert (child != null) : "child equals to null";
				List<Double> edges = graph.getEdgeData(node, child);
				for (int i = 0; i < edges.size(); i++) {
					assert (edges.get(i) != null) : "edge equals to null";
				}
			}
		}
	}
}
