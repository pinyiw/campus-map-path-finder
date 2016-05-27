package hw8.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hw8.Location;
import hw8.Pair;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Location class.
 * 
 * @author pinyiw
 */
public final class LocationTest {

	@Test
	public void testConstructor() {
		new Location("A", "a", "1", "2");
		new Location("B", "b", "1", "1");
		new Location("C", "c", "1", "3");
	}
	
	@Test
	public void testShortName() {
		Location a = new Location("A", "a", "1", "2");
		Location b = new Location("B", "b", "1", "1");
		Location c = new Location("C", "c", "1", "3");
		assertTrue(a.shortName().equals("A"));
		assertTrue(b.shortName().equals("B"));
		assertTrue(c.shortName().equals("C"));
	}
	
	@Test
	public void testLongName() {
		Location a = new Location("A", "a", "1", "2");
		Location b = new Location("B", "b", "1", "1");
		Location c = new Location("C", "c", "1", "3");
		assertTrue(a.longName().equals("a"));
		assertTrue(b.longName().equals("b"));
		assertTrue(c.longName().equals("c"));
	}
	
	@Test
	public void testXCoordinates() {
		Location a = new Location("A", "a", "1", "2");
		Location b = new Location("B", "b", "1", "1");
		Location c = new Location("C", "c", "1", "3");
		assertTrue(a.x().equals("1"));
		assertTrue(b.x().equals("1"));
		assertTrue(c.x().equals("1"));
	}
	
	@Test
	public void testYCoordinates() {
		Location a = new Location("A", "a", "1", "2");
		Location b = new Location("B", "b", "1", "1");
		Location c = new Location("C", "c", "1", "3");
		assertTrue(a.y().equals("2"));
		assertTrue(b.y().equals("1"));
		assertTrue(c.y().equals("3"));
	}
	
	@Test
	public void testXY() {
		Location a = new Location("A", "a", "1", "2");
		Location b = new Location("B", "b", "1", "1");
		Location c = new Location("C", "c", "1", "3");
		assertTrue(a.xy().equals(new Pair<String, String>("1", "2")));
		assertTrue(b.xy().equals(new Pair<String, String>("1", "1")));
		assertTrue(c.xy().equals(new Pair<String, String>("1", "3")));
	}
}
