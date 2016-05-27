package hw8;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hw5.Graph;
import hw5.GraphNode;
import hw7.GraphNodePath;
import hw7.MarvelPaths2;
import hw8.DataParser.MalformedDataException;
import javafx.util.Pair;

/**
 * The main class that find the shortest distance between buildings in UW.
 */
public class UWCampusPathMain {
	
	/** the UWCampusPaths that process and stores all the buildings and
	 *  location information. */
	private static UWCampusPaths uwcp;
	
	// This is not an ADT.
	// Abstraction function and representation invariant will normally be here.
	
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
	
	/**
	 * Initializes the buildings and graph
	 * 
	 * @throws MalformedDataException if the file names have invalid format
	 */
	private static void init() throws MalformedDataException {
		uwcp = new UWCampusPaths("src/hw8/data/campus_buildings.dat",
											"src/hw8/data/campus_paths.dat");
	}
	
	/**
	 * Prints all the buildings.
	 */
	private static void printBuildings() {
		System.out.println("Buildings:");
		Map<String, Location> buildings = uwcp.getBuildingsInfo();
		for (String shortName: buildings.keySet()) {
			System.out.println("\t" + shortName + ": " + 
						buildings.get(shortName).longName());
		}
		System.out.println();
	}
	
	/**
	 * Prompt and prints the shortest route from one building to another.
	 * 
	 * @param console the scanner it interact with.
	 */
	private static void printRoute(Scanner console) {
		System.out.print("Abbreviated name of starting building: ");
		String start = console.nextLine().trim();
		System.out.print("Abbreviated name of ending building: ");
		String end = console.nextLine().trim();
		
		Map<String, Location> buildings = uwcp.getBuildingsInfo();
		if (buildings.keySet().contains(start) && buildings.keySet().contains(end)) {
			Location first = buildings.get(start);
			Location second = buildings.get(end);
			System.out.println("Path from " + first.longName() + " to " + 
													second.longName() + ":");
			GraphNodePath<Pair<String, String>> gnp = 
						uwcp.findPath(first.xy(), second.xy());
			List<GraphNode<Pair<String, String>>> path = gnp.getPath();
			Pair<String, String> cur = path.get(0).getName();
			Double totalDist = 0.0;
			for (int i = 1; i < path.size(); i++) {
				Pair<String, String> next = path.get(i).getName();
				Double distance = uwcp.getDistance(cur, next);
				Double curX = Double.parseDouble(cur.getKey());
				Double curY = Double.parseDouble(cur.getValue());
				Double nextX = Double.parseDouble(next.getKey());
				Double nextY = Double.parseDouble(next.getValue());
				Double fraction = Math.atan2(nextY - curY, nextX - curX) / Math.PI * -8.0;
				String direction = "";
				if (fraction > -7.0 && fraction < -5.0) {
					direction = "SW";
				} else if (fraction >= -5.0 && fraction <= -3.0) {
					direction = "S";
				} else if (fraction > -3.0 && fraction < -1.0) {
					direction = "SE";
				} else if (fraction >= -1.0 && fraction <= 1.0) {
					direction = "E";
				} else if (fraction > 1.0 && fraction < 3.0) {
					direction = "NE";
				} else if (fraction >= 3.0 && fraction <= 5.0) {
					direction = "N";
				} else if (fraction > 5.0 && fraction < 7.0) {
					direction = "NW";
				} else {
					direction = "W";
				}
				System.out.println(String.format("\tWalk %1.0f feet " + direction +
									" to (%2.0f, %3.0f)", distance, nextX, nextY));
				cur = next;
				totalDist += distance;
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
	
	/**
	 * Prints the available commend.
	 */
	private static void printMenu() {
		System.out.println("Menu:");
		System.out.println("\tr to find a route");
		System.out.println("\tb to see a list of all buildings");
		System.out.println("\tq to quit");
		System.out.println();
	}
	
	/**
	 * Prints unknown option.
	 */
	private static void printUnknownOption() {
		System.out.println("Unknown option");
		System.out.println();
	}
}
