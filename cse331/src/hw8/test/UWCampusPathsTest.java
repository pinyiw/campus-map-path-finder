package hw8.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hw5.GraphNode;
import hw7.GraphNodePath;
import hw8.DataParser.MalformedDataException;
import hw8.Location;
import hw8.UWCampusPaths;
import javafx.util.Pair;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the UWCampusPaths class.
 * 
 * @author pinyiw
 */
public final class UWCampusPathsTest {
	
	private UWCampusPaths uwcp;
	private Map<String, Location> buildings;
	
	@Before
	public void init() throws MalformedDataException {
		uwcp = new UWCampusPaths("src/hw8/data/campus_buildings.dat",
				"src/hw8/data/campus_paths.dat");
		buildings = uwcp.getBuildingsInfo();
	}
	
	@Test
	public void testConstructor() throws MalformedDataException {
		new UWCampusPaths("src/hw8/data/campus_buildings.dat",
											"src/hw8/data/campus_paths.dat");
	}
	
	@Test
	public void testFindPath() {
		String start = "MUS";
		String end = "MGH";
		Location first = buildings.get(start);
		Location second = buildings.get(end);
		GraphNodePath<Pair<String, String>> gnp = 
					uwcp.findPath(first.xy(), second.xy());
		List<GraphNode<Pair<String, String>>> list = gnp.getPath();
		assertEquals(list.size(), 25);
		start = "CSE";
		end = "PAB";
		first = buildings.get(start);
		second = buildings.get(end);
		gnp = uwcp.findPath(first.xy(), second.xy());
		list = gnp.getPath();
		assertEquals(list.size(), 26);
	}
	
	@Test
	public void testGetDistance() {
		String start = "MUS";
		String end = "MGH";
		Location first = buildings.get(start);
		Location second = buildings.get(end);
		GraphNodePath<Pair<String, String>> gnp = 
				uwcp.findPath(first.xy(), second.xy());
		List<GraphNode<Pair<String, String>>> list = gnp.getPath();
		for (int i = 0; i < list.size() - 1; i++) {
			Pair<String, String> cur = list.get(i).getName();
			Pair<String, String> next = list.get(i + 1).getName();
			assertTrue(uwcp.getDistance(cur, next) > 0.0);
		}
	}
}
