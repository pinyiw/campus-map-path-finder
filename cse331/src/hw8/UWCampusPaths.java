package hw8;

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

public class UWCampusPaths {
	
	public static Map<String, Location> loadBuildings(String buildingsFileName)
			throws MalformedDataException {
		Map<String, Location> buildings = new TreeMap<String, Location>();
		DataParser.parseBuildingsData(buildingsFileName, buildings);
		return buildings;
	}
	
	public static Graph<Pair<String, String>, Double> loadGraph(String pathsFileName) 
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
	
	public static GraphNodePath<Pair<String, String>> findPath(Graph<Pair<String, String>,
				Double> graph, Pair<String, String> start, Pair<String,String> dest) {
		return MarvelPaths2.findPath(graph, start, dest);
	}
	
}
