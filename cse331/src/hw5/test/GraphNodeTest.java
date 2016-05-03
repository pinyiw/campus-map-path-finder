package hw5.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hw5.GraphNode;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the GraphNode class.
 * 
 * @author pinyiw
 */

public final class GraphNodeTest {
	
	
	@Test
	public void testConstructor() {
		new GraphNode(" ");
		new GraphNode("1");
		new GraphNode("abc");
		new GraphNode("#bv");
	}
	
	@Test
	public void testGetName() {
		GraphNode a = new GraphNode(" ");
		GraphNode b = new GraphNode("abc");
		GraphNode c = new GraphNode("1a2b3c");
		assertEquals(" ", a.getName());
		assertEquals("abc", b.getName());
		assertEquals("1a2b3c", c.getName());
	}
	
	@Test
	public void testEquals() {
		GraphNode a1 = new GraphNode(" ");
		GraphNode a2 = new GraphNode(" ");
		GraphNode b1 = new GraphNode("abc");
		GraphNode b2 = new GraphNode("abc");
		GraphNode c1 = new GraphNode("1a2b3c");
		GraphNode c2 = new GraphNode("1a2b3c");
		assertTrue(a1.equals(a2));
		assertTrue(b1.equals(b2));
		assertTrue(c1.equals(c2));
	}
	
	@Test
	public void testNotEquals() {
		GraphNode a1 = new GraphNode(" ");
		GraphNode a2 = new GraphNode("  ");
		GraphNode b1 = new GraphNode("abc");
		GraphNode b2 = null;
		GraphNode c1 = new GraphNode("123");
		GraphNode c2 = new GraphNode("234");
		GraphNode d = new GraphNode("c");
		assertFalse(a1.equals(a2));
		assertFalse(b1.equals(b2));
		assertFalse(c1.equals(c2));
		assertFalse(d.equals(new Object()));
	}
	
	@Test
	public void testHashCode() {
		GraphNode a1 = new GraphNode(" ");
		GraphNode a2 = new GraphNode(" ");
		GraphNode b1 = new GraphNode("abc");
		GraphNode b2 = new GraphNode("abc");
		GraphNode c1 = new GraphNode("1a2b3c");
		GraphNode c2 = new GraphNode("1a2b3c");
		assertEquals(a1.hashCode(), a2.hashCode());
		assertEquals(b1.hashCode(), b2.hashCode());
		assertEquals(c1.hashCode(), c2.hashCode()); 
	}
}
