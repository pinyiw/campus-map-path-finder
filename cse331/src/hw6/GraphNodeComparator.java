package hw6;

import java.util.Comparator;

import hw5.GraphNode;

public class GraphNodeComparator<T extends Comparable<T>>
			implements Comparator<GraphNode<T>> {

	@Override
	public int compare(GraphNode<T> o1, GraphNode<T> o2) {
		// TODO Auto-generated method stub
		return o1.getName().compareTo(o2.getName());
	}
}
