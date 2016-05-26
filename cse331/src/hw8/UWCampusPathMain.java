package hw8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import hw5.Graph;
import hw8.DataParser.MalformedDataException;

public class UWCampusPathMain {
	
	private static Graph<Location, Double> graph;
	
	private static List<Location> sortedBuildings;
	
	public static void main(String[] args) throws MalformedDataException {
		init();
		printMenu();
		Scanner console = new Scanner(System.in);
		while (true) {
			System.out.print("Enter an option ('m' to see the menu): ");
			String option = console.nextLine().trim();
			if (option.equals("m")) {
				printMenu();
			} else if (option.equals("r")) {
				printRoute(console);
			} else if (option.equals("b")) {
				printBuildings();
			} else if (option.equals("q")) {
				return;
			} else if (option.startsWith("#")) {
				System.out.println(option);
			} else {
				printUnknownOption();
			}
		}
	}
	
	public static void init() throws MalformedDataException {
		Set<Location> buildings = UWCampusPaths.loadBuildings("src/hw8/data/campus_buildings.dat");
		graph = UWCampusPaths.loadGraph(buildings, "src/hw8/data/campus_paths.dat");
		sortedBuildings = new ArrayList<Location>();
		sortedBuildings.addAll(buildings);
		Collections.sort(sortedBuildings, new LocationComparator());
	}
	
	public static void printBuildings() {
		System.out.println("Buildings:");
		//
		for (int i = 0; i < sortedBuildings.size(); i++) {
			System.out.println("\t" + sortedBuildings.get(i).shortName() + ": " +
					sortedBuildings.get(i).longName());
		}
		System.out.println();
	}
	
	public static void printRoute(Scanner console) {
		System.out.print("Abbreviated name of starting building: ");
		String start = console.nextLine().trim();
		System.out.print("Abbreviated name of ending building: ");
		String end = console.nextLine().trim();
		//
		
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
