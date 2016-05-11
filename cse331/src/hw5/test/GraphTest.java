package hw5.test;

import hw5.GraphNode;
import hw5.Graph;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;


/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Graph class.
 * 
 * @author pinyiw
 */

public final class GraphTest {
	
	private Graph zeroNode;
	private Graph oneNode;
	private Graph twoNode;
	private Graph threeNode;
	
	private GraphNode a = new GraphNode("a");
	private GraphNode b = new GraphNode("b");
	private GraphNode c = new GraphNode("c");

	private void init() {
		List<GraphNode> list = new LinkedList<GraphNode>();
		list.add(a);
		
		zeroNode = new Graph();
		oneNode = new Graph(list);
		
		list.add(b);
		twoNode = new Graph(list);
		
		list.add(c);
		threeNode = new Graph(list);
	}
	
	@Test
	public void testEmptyNodeConstructor() {
		Graph g = new Graph();
		assertTrue(g.isEmpty());
	}
	
	@Test
	public void testListConstructor() {
		init();
		Graph g = new Graph(new LinkedList<GraphNode>());
		assertTrue(g.isEmpty());
	}
	
	@Test
	public void testContains() {
		init();
		assertTrue(oneNode.contains(a));
		assertTrue(twoNode.contains(a));
		assertTrue(twoNode.contains(b));
	}
	
	@Test
	public void testDoesNotContains() {
		init();
		assertFalse(zeroNode.contains(a));
		assertFalse(zeroNode.contains(b));
		assertFalse(oneNode.contains(b));
		assertFalse(twoNode.contains(c));
	}
	
	@Test
	public void testNodeSize() {
		init();
		assertEquals(zeroNode.nodeSize(), 0);
		assertEquals(oneNode.nodeSize(), 1);
		assertEquals(twoNode.nodeSize(), 2);
	}
	
	@Test
	public void testIsEmpty() {
		init();
		assertTrue(zeroNode.isEmpty());
		assertFalse(oneNode.isEmpty());
		assertFalse(twoNode.isEmpty());
	}
	
	@Test
	public void testAddNode() {
		Graph g = new Graph();
		assertTrue(g.addNode(a));
		assertTrue(g.addNode(b));
		assertFalse(g.addNode(a));
		assertTrue(g.contains(a));
		assertTrue(g.contains(b));
	}
	
	@Test
	public void testIsNotConnected() {
		init();
		assertFalse(twoNode.isConnected(a, b));
		assertFalse(threeNode.isConnected(a, b));
		assertFalse(threeNode.isConnected(b, c));
		assertFalse(threeNode.isConnected(a, c));
	}

	@Test
	public void testAddEdge() {
		init();
		twoNode.addEdge(a, b, "");
		threeNode.addEdge(a, b, "");
		assertTrue(twoNode.isConnected(a, b));
		assertFalse(threeNode.isConnected(b, a));
		assertTrue(threeNode.isConnected(a, b));
		threeNode.addEdge(b, c, "");
		assertTrue(threeNode.isConnected(a, b));
		assertTrue(threeNode.isConnected(b, c));
		assertFalse(threeNode.isConnected(a, c));
		assertFalse(threeNode.isConnected(b, a));
		assertFalse(threeNode.isConnected(c, b));
	}
	
	@Test
	public void testSelfConnect() {
		init();
		threeNode.addEdge(a, a, "");
		assertTrue(threeNode.isConnected(a, a));
		assertFalse(threeNode.isConnected(b, b));
		assertFalse(threeNode.isConnected(c, c));		
	}
	
	@Test
	public void testMultipleConnectionToOneNode() {
		init();
		threeNode.addEdge(a, a, "");
		threeNode.addEdge(a, b, "");
		threeNode.addEdge(a, c, "");
		assertTrue(threeNode.isConnected(a, a));
		assertTrue(threeNode.isConnected(a, b));
		assertTrue(threeNode.isConnected(a, c));
	}
	
	@Test
	public void testGetEdgeData() {
		init();
		String[] arr = {"first", "second", "third", "fourth", "fifth"};
		threeNode.addEdge(a, b, arr[0]);
		threeNode.addEdge(a, b, arr[1]);
		threeNode.addEdge(a, c, arr[2]);
		threeNode.addEdge(b, b, arr[2]);
		threeNode.addEdge(b, b, arr[3]);
		threeNode.addEdge(b, b, arr[4]);
		assertTrue(threeNode.getEdgeData(b, a) == null);
		assertTrue(threeNode.getEdgeData(b, c) == null);
		List<String> list1 = threeNode.getEdgeData(a, b);
		for (int i = 0; i < 2; i++) {
			assertTrue(list1.contains(arr[i]));
			assertFalse(list1.contains(arr[arr.length - i - 1]));
		}
		assertTrue(threeNode.getEdgeData(a, c).contains(arr[2]));
		List<String> list2 = threeNode.getEdgeData(b, b);
		for (int i = 0; i < 3; i++) {
			assertTrue(list2.contains(arr[i + 2]));
		}
	}
	
	@Test
	public void testChildNode() {
		init();
		GraphNode[] arr = {a, b, c};
		threeNode.addEdge(a, a, "");
		threeNode.addEdge(a, b, "");
		threeNode.addEdge(a, c, "");
		threeNode.addEdge(b, c, "");
		threeNode.addEdge(b, b, "");
		threeNode.addEdge(c, a, "");
		Set<GraphNode> setA = threeNode.childNode(a);
		for (int i = 0; i < 3; i++) {
			assertTrue(setA.contains(arr[i]));
		}
		Set<GraphNode> setB = threeNode.childNode(b);
		for (int i = 1; i < 3; i++) {
			assertTrue(setB.contains(arr[i]));
		}
		assertFalse(setB.contains(a));
		Set<GraphNode> setC = threeNode.childNode(c);
		for (int i = 1; i < 3; i++) {
			assertFalse(setC.contains(arr[i]));
		}
		assertTrue(setC.contains(a));
	}
	
	@Test
	public void testIsolatedGraphChildNode() {
		init();
		assertEquals(threeNode.childNode(a).size(), 0);
		assertEquals(threeNode.childNode(b).size(), 0);
		assertEquals(threeNode.childNode(c).size(), 0);
	}
	
	@Test
	public void testNodes() {
		init();
		Set<GraphNode> set0 = zeroNode.nodes();
		assertFalse(set0.contains(a));
		assertFalse(set0.contains(b));
		assertFalse(set0.contains(c));
		
		Set<GraphNode> set1 = oneNode.nodes();
		assertTrue(set1.contains(a));
		assertFalse(set1.contains(b));
		assertFalse(set1.contains(c));
		
		Set<GraphNode> set2 = twoNode.nodes();
		assertTrue(set2.contains(a));
		assertTrue(set2.contains(b));
		assertFalse(set2.contains(c));
	}
}