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
		new GraphNode<String>(" ");
		new GraphNode<String>("1");
		new GraphNode<String>("abc");
		new GraphNode<String>("#bv");
	}
	
	@Test
	public void testGetName() {
		GraphNode<String> a = new GraphNode<String>(" ");
		GraphNode<String> b = new GraphNode<String>("abc");
		GraphNode<String> c = new GraphNode<String>("1a2b3c");
		assertEquals(" ", a.getName());
		assertEquals("abc", b.getName());
		assertEquals("1a2b3c", c.getName());
	}
	
	@Test
	public void testEquals() {
		GraphNode<String> a1 = new GraphNode<String>(" ");
		GraphNode<String> a2 = new GraphNode<String>(" ");
		GraphNode<String> b1 = new GraphNode<String>("abc");
		GraphNode<String> b2 = new GraphNode<String>("abc");
		GraphNode<String> c1 = new GraphNode<String>("1a2b3c");
		GraphNode<String> c2 = new GraphNode<String>("1a2b3c");
		assertTrue(a1.equals(a2));
		assertTrue(b1.equals(b2));
		assertTrue(c1.equals(c2));
	}
	
	@Test
	public void testNotEquals() {
		GraphNode<String> a1 = new GraphNode<String>(" ");
		GraphNode<String> a2 = new GraphNode<String>("  ");
		GraphNode<String> b1 = new GraphNode<String>("abc");
		GraphNode<String> b2 = null;
		GraphNode<String> c1 = new GraphNode<String>("123");
		GraphNode<String> c2 = new GraphNode<String>("234");
		GraphNode<String> d = new GraphNode<String>("c");
		assertFalse(a1.equals(a2));
		assertFalse(b1.equals(b2));
		assertFalse(c1.equals(c2));
		assertFalse(d.equals(new Object()));
	}
	
	@Test
	public void testHashCode() {
		GraphNode<String> a1 = new GraphNode<String>(" ");
		GraphNode<String> a2 = new GraphNode<String>(" ");
		GraphNode<String> b1 = new GraphNode<String>("abc");
		GraphNode<String> b2 = new GraphNode<String>("abc");
		GraphNode<String> c1 = new GraphNode<String>("1a2b3c");
		GraphNode<String> c2 = new GraphNode<String>("1a2b3c");
		assertEquals(a1.hashCode(), a2.hashCode());
		assertEquals(b1.hashCode(), b2.hashCode());
		assertEquals(c1.hashCode(), c2.hashCode()); 
	}
}
