package hw8;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hw5.Graph;
import hw5.GraphNode;
import hw7.GraphNodePath;
import hw8.DataParser.MalformedDataException;
import javafx.util.Pair;

public class UWCampusPathMain {
	
	private static Graph<Pair<String, String>, Double> graph;
	
	private static Map<String, Location> buildings;
	//private static List<Location> sortedBuildings;
	
	public static void main(String[] args) throws MalformedDataException {
		init();
		printMenu();
		Scanner console = new Scanner(System.in);
		while (true) {
			System.out.print("Enter an option ('m' to see the menu): ");
			String option = console.nextLine().trim();
			while(option.length() == 0 || option.startsWith("#")) {
				System.out.println(option);
				option = console.nextLine().trim();
			}
			if (option.equals("m")) {
				printMenu();
			} else if (option.equals("r")) {
				printRoute(console);
			} else if (option.equals("b")) {
				printBuildings();
			} else if (option.equals("q")) {
				return;
			} else {
				printUnknownOption();
			}
		}
	}
	
	public static void init() throws MalformedDataException {
		buildings = UWCampusPaths.loadBuildings("src/hw8/data/campus_buildings.dat");
		graph = UWCampusPaths.loadGraph("src/hw8/data/campus_paths.dat");
	}
	
	public static void printBuildings() {
		System.out.println("Buildings:");
		//
		for (String shortName: buildings.keySet()) {
			System.out.println("\t" + shortName + ": " + 
						buildings.get(shortName).longName());
		}
		System.out.println();
	}
	
	public static void printRoute(Scanner console) {
		System.out.print("Abbreviated name of starting building: ");
		String start = console.nextLine().trim();
		System.out.print("Abbreviated name of ending building: ");
		String end = console.nextLine().trim();
		//
		if (buildings.keySet().contains(start) && buildings.keySet().contains(end)) {
			Location first = buildings.get(start);
			Location second = buildings.get(end);
			System.out.println("Path from " + first.longName() + " to " + 
													second.longName() + ":");
			GraphNodePath<Pair<String, String>> gnp = 
					UWCampusPaths.findPath(graph, first.xy(), second.xy());
			List<GraphNode<Pair<String, String>>> path = gnp.getPath();
			Pair<String, String> cur = path.get(0).getName();
			Double totalDist = 0.0;
			for (int i = 1; i < path.size(); i++) {
				GraphNode<Pair<String, String>> curNode = 
						new GraphNode<Pair<String, String>>(cur);
				Pair<String, String> next = path.get(i).getName();
				GraphNode<Pair<String, String>> nextNode =
								new GraphNode<Pair<String, String>>(next);
				List<Double> distList = graph.getEdgeData(curNode, nextNode);
				Collections.sort(distList);
				Double curX = Double.parseDouble(cur.getKey());
				Double curY = Double.parseDouble(cur.getValue());
				Double nextX = Double.parseDouble(next.getKey());
				Double nextY = Double.parseDouble(next.getValue());
				Double theta = Math.atan2(nextX - curX, nextY - curY);
				
				String direction = "";
				System.out.println(String.format("\tWalk %1.0f feet " + direction +
									" to (%2.0f, %3.0f)",distList.get(0), nextX, nextY));
				cur = next;
				totalDist += distList.get(0);
			}
			System.out.println(String.format("Total distance: %.0f feet", totalDist));
		} else {
			if (!buildings.keySet().contains(start)) {
				System.out.println("Unknown building: " + start);
			}
			if (!buildings.keySet().contains(end)) {
				System.out.println("Unknown building: " + end);
			}
		}
		System.out.println();
	}
	
	public static void printMenu() {
		System.out.println("Menu:");
		System.out.println("\tr to find a route");
		System.out.println("\tb to see a list of all buildings");
		System.out.println("\tq to quit");
		System.out.println();
	}
	
	public static void printUnknownOption() {
		System.out.println("Unknown option");
		System.out.println();
	}
}
