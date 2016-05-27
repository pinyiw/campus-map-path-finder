package hw8;
import java.io.*;
import java.util.*;

import javafx.util.Pair;

/**
 * Parser utility to load the UW map dataset.
 */
public class DataParser {
	
	// This is not an ADT.
	// Abstraction Function and Representation invariant would normally be here.
		
	/**
	 * A checked exception class for bad data files
	 */
	@SuppressWarnings("serial")
	public static class MalformedDataException extends Exception {
		public MalformedDataException() {}
		
		public MalformedDataException(String message) {
			super(message);
		}
		
		public MalformedDataException(Throwable cause) {
			super(cause);
		}
		
		public MalformedDataException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	/**
	 * Reads the campus building dataset.
	 * Each line of the input file contains a short name, a long name, an x and
	 * a y of a place, separated by a tab character.
	 * 
	 * @requires fileName is a valid file path.
	 * @param fileName the file that will be read.
	 * @param buildings the set that all buildings will be stored, typically
	 * 		  empty when the routine is called.
	 * @modifies buildings
	 * @effects fills locations into buildings.
	 * @throws MalformedDataException if  the file is not well-formed:
	 * 			each line contains exactly four tokens separated by a tab.
	 */
	public static void parseBuildingsData(String fileName, 
			Map<String, Location> buildings) throws MalformedDataException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				
				if (inputLine.length() == 0) {
					continue;
				}
				
				String[] tokens = inputLine.split("\t");
				if (tokens.length != 4) {
					throw new MalformedDataException("Line should contain exactly"
							+ "three tab:" + inputLine);
				}
				
				String shortName = tokens[0];
				String longName = tokens[1];
				String x = tokens[2];
				String y = tokens[3];
				
				if (shortName != null && shortName.length() != 0) {
					buildings.put(shortName, new Location(shortName, longName, x, y));
				}
			}
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.toString());
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	/**
	 * Reads the campus paths dataset.
	 * The input file contains a straight-line segments of walking paths. For
	 * each endpoint of a path segment, there is a line in the file listing the
	 * pixel coordinates of that point, followed by a tab-indented line for
	 * each endpoint to which it is connected with a path segment.
	 * Each indented line lists the coordinates of the other endpoint and the
	 * distance of the segment between them in feet.
	 * 
	 * @requires fileName is a valid file path.
	 * @param fileName the file that will be read.
	 * @param paths the map in which all paths will be stored, typically empty
	 * 		  the routine is called.
	 * @modifies paths
	 * @effects fills 'paths' with key starting point of a path with a
	 * 			Pair storing x and y and its name and value. While the value of
	 * 			paths is a Pair that stores another Pair of x and y as name and
	 * 			a Double of distance as value.
	 * 			Represent Pair as (A, B) where A is the key and B is the value.
	 * 			For example, (a, b) is the start and (x1, y1), (x2, y2), ...
	 * 			are position reachable from (a, b) and has distance d1, d2, ...
	 * 			'paths' should look like:
	 * 			(a, b) -> [((x1, y1), d1), ((x2, y2), d2), ...]
	 * @throws MalformedDataException if the file is not well-formed as
	 * 		   described in the method comments.
	 */
	public static void parsePathsData(String fileName,
			Map<Pair<String, String>, 
				List<Pair<Pair<String, String>, Double>>> paths)
			throws MalformedDataException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			String first = reader.readLine();
			while (first!= null) {
				String[] xy = first.split(",");
				if (xy.length != 2) {
					throw new MalformedDataException("Line should have two"
							+ "coordinates: " + first);
				}
				String x1 = xy[0];
				String y1 = xy[1];
				Pair<String, String> start = new Pair<String, String>(x1, y1);
				if (!paths.containsKey(first)) {
					paths.put(start, 
							new ArrayList<Pair<Pair<String, String>, Double>>());
				}
				String next = reader.readLine();
				
				while (next != null && next.startsWith("\t")) {
					next = next.trim();
					String[] tokens = next.split(": ");
					if (tokens.length != 2) {
						throw new MalformedDataException("Line should have two"
								+ "coordinates and one distance: " + next);
					}
					Double distance = Double.parseDouble(tokens[1]);
					String[] xy2 = tokens[0].split(",");
					if (xy2.length != 2) {
						throw new MalformedDataException("Line should have two"
								+ "coordinates: " + next);
					}
					String x2 = xy2[0];
					String y2 = xy2[1];
					Pair<String, String> dest = new Pair<String, String>(x2, y2);
					Pair<Pair<String, String>, Double> data = 
							new Pair<Pair<String, String>, Double>(dest, distance);
					paths.get(start).add(data);
					next = reader.readLine();
				}
				first = next;
			}
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.toString());
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	private static void print(String[] arr) {
		System.out.print("[" + arr[0]);
		for (int i = 1; i < arr.length; i++) {
			System.out.print(", " + arr[i]);
		}
		System.out.println("]");
	}
}
