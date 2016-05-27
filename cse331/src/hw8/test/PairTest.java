package hw8.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hw8.Pair;

/**
 * This class contains a set of test cases that can be test the
 * implementation of the Pair class.
 * 
 * @author pinyiw
 */
public final class PairTest {
	
	@Test
	public void testConstructor() {
		new Pair<String, String>("1", "1");
		new Pair<String, String>("1", "2");
		new Pair<String, String>("1", "3");
	}
	
	@Test
	public void testGetKey() {
		Pair<String, String> a = new Pair<String, String>("1", "1");
		Pair<String, String> b = new Pair<String, String>("1", "2");
		Pair<String, String> c = new Pair<String, String>("1", "3");
		assertEquals(a.getKey(), "1");
		assertEquals(b.getKey(), "1");
		assertEquals(c.getKey(), "1");
	}
	
	@Test
	public void testGetValue() {
		Pair<String, String> a = new Pair<String, String>("1", "1");
		Pair<String, String> b = new Pair<String, String>("1", "2");
		Pair<String, String> c = new Pair<String, String>("1", "3");
		assertEquals(a.getValue(), "1");
		assertEquals(b.getValue(), "2");
		assertEquals(c.getValue(), "3");
	}
	
	@Test
	public void testEquals() {
		Pair<String, String> a = new Pair<String, String>("1", "1");
		Pair<String, String> b = new Pair<String, String>("1", "2");
		Pair<String, String> c = new Pair<String, String>("1", "3");
		assertFalse(a.equals(b));
		assertFalse(b.equals(c));
		assertFalse(a.equals(c));
		assertTrue(a.equals(new Pair<String, String>("1", "1")));
		assertTrue(b.equals(new Pair<String, String>("1", "2")));
		assertTrue(c.equals(new Pair<String, String>("1", "3")));
	}
}
