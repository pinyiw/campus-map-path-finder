package hw6;

import java.util.Comparator;

import hw5.GraphNode;

public class StringGraphNodeComparator implements Comparator<GraphNode<String>> {

	@Override
	public int compare(GraphNode<String> o1, GraphNode<String> o2) {
		// TODO Auto-generated method stub
		return o1.getName().compareTo(o2.getName());
	}
	
}
