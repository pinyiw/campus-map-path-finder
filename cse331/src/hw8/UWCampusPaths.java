package hw8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hw5.Graph;
import hw5.GraphNode;
import hw7.GraphNodePath;
import hw7.MarvelPaths2;
import hw8.DataParser.MalformedDataException;
import javafx.util.Pair;

public class UWCampusPaths {
	
	public static Set<Location> loadBuildings(String buildingsFileName)
			throws MalformedDataException {
		Set<Location> buildings = new HashSet<Location>();
		DataParser.parseBuildingsData(buildingsFileName, buildings);
		return buildings;
	}
	
	public static Graph<Location, Double> loadGraph(Set<Location> buildings,
			String pathsFileName) throws MalformedDataException {
		Map<Pair<String, String>, List<Pair<Pair<String, String>, Double>>> paths =
				new HashMap<Pair<String, String>, List<Pair<Pair<String, String>, Double>>>();
		DataParser.parsePathsData(pathsFileName, paths);
		Graph<Location, Double> graph = new Graph<Location, Double>();
		for (Location l: buildings) {
			graph.addNode(new GraphNode<Location>(l));
		}
		for (Pair<String, String> xy: paths.keySet()) {
			GraphNode<Location> cur = new GraphNode<Location>(new Location(xy));
			if (!graph.contains(cur)) {
				graph.addNode(cur);
			}
			List<Pair<Pair<String, String>, Double>> curList = paths.get(xy);
			for (int i = 0; i < curList.size(); i++) {
				Pair<String, String> xy2 = curList.get(i).getKey();
				Double dist = curList.get(i).getValue();
				GraphNode<Location> dest = new GraphNode<Location>(new Location(xy2));
				if (!graph.contains(dest)) {
					graph.addNode(dest);
				}
				graph.addEdge(cur, dest, dist);
			}
		}
		return graph;
	}
	
	public static void findPath(Graph<Location, Double> graph, Location start,
															Location dest) {
		GraphNodePath<Location> gnp = MarvelPaths2.findPath(graph, start, dest);
		
	}
	
}
