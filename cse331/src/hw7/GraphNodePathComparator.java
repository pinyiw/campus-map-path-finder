package hw7;

import java.util.Comparator;

public class GraphNodePathComparator<T> implements Comparator<GraphNodePath<T>>{

	@Override
	public int compare(GraphNodePath<T> o1, GraphNodePath<T> o2) {
		// TODO Auto-generated method stub
		return o1.length().compareTo(o2.length());
	}

}
