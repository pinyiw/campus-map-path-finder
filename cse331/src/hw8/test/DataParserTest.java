package hw8.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import hw8.DataParser;
import hw8.DataParser.MalformedDataException;
import javafx.util.Pair;
import hw8.Location;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the DataParser class.
 * 
 * @author pinyiw
 */
public final class DataParserTest {
	
	@Test
	public void testParseBuildingsData() throws MalformedDataException {
		Map<String, Location> buildings = new HashMap<String, Location>();
		DataParser.parseBuildingsData("src/hw8/data/campus_buildings.dat", 
														buildings);
		assertEquals(buildings.size(), 51);
		for (String name: buildings.keySet()) {
			assertTrue(name != null);
			assertTrue(name.length() > 0);
			assertTrue(buildings.get(name) != null);
		}
	}
	
	@Test
	public void testParsePathsData() throws MalformedDataException {
		Map<Pair<String, String>, List<Pair<Pair<String, String>, Double>>> paths =
				new HashMap<Pair<String, String>, List<Pair<Pair<String, String>, Double>>>();
		DataParser.parsePathsData("src/hw8/data/campus_paths.dat", paths);
		assertEquals(paths.size(), 2067);
		for (Pair<String, String> start: paths.keySet()) {
			assertTrue(start != null);
			assertTrue(paths.get(start) != null);
		}
	}
}
