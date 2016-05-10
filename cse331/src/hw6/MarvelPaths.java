package hw6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import hw5.Graph;
import hw5.GraphNode;


/**
 * <b>MarvelPaths</b>
 * 
 * @author pinyiw
 */

public class MarvelPaths {
	
	private Graph graph;
	
	// Abstraction Function:
	//
	//
	// Representation invariant for every MarvelPaths m:
	//	
	
	public MarvelPaths(String fileName) {
		Set<String> characters = new HashSet<String>();
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		try {
			MarvelParser.parseData(fileName, characters, books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}
		List<GraphNode> chars = new ArrayList<GraphNode>();
		for (String character: characters) {
			chars.add(new GraphNode(character));
		}
		graph = new Graph(chars);
		for (String book: books.keySet()) {
			List<String> curChars = books.get(book);
			for (int i = 0; i < curChars.size() - 1; i++) {
				GraphNode first = new GraphNode(curChars.get(i));
				for (int j = i + 1; j < curChars.size(); j++) {
					GraphNode second = new GraphNode(curChars.get(j));
					graph.addEdge(first, second, book);
					graph.addEdge(second, first, book);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param char1
	 * @param char2
	 * @return an empty list of string if connection between char1 and char2 was not found, otherwise
	 * 		   a list of string .......
	 */
	public List<String> search(String char1, String char2) {
		GraphNode start = new GraphNode(char1);
		GraphNode dest = new GraphNode(char2);
		if (char1 == null || char2 == null || !graph.contains(start)
				|| !graph.contains(dest)) {
			throw new IllegalArgumentException();
		}
		Queue<GraphNode> workList = new LinkedList<GraphNode>();
		Map<GraphNode, List<GraphNode>> paths = 
							new HashMap<GraphNode, List<GraphNode>>();
		
		List<GraphNode> resultPath = new ArrayList<GraphNode>();
		workList.add(start);
		paths.put(start, new ArrayList<GraphNode>());
		while (!workList.isEmpty()) {
			GraphNode cur = workList.remove();
			if (cur.equals(dest)) {
				resultPath = paths.get(cur);
				// add the start node at the start of the list and dest node
				// at the end of the list
				resultPath.add(0, start);
				resultPath.add(dest);
				workList.clear();
			} else {
				for (GraphNode neighbor: graph.childNode(cur)) {
					if (!paths.containsKey(neighbor)) {
						List<GraphNode> curPath = new ArrayList<GraphNode>();
						curPath.addAll(paths.get(cur));
						curPath.add(neighbor);
						paths.put(neighbor, curPath);
						workList.add(neighbor);
					}
				}
			}
		}
		return processPathToReadableList(resultPath);
	}
	
	private List<String> processPathToReadableList(List<GraphNode> path) {
		List<String> result = new ArrayList<String>();
		if (!path.isEmpty()) {
			for (int i = 0; i < path.size() - 1; i++) {
				result.add(path.get(i + 1).getName());
				List<String> edges = graph.getEdgeData(path.get(i), 
														path.get(i + 1));
				
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		MarvelPaths mp = new MarvelPaths("src/hw6/data/staffSuperheroes.tsv");
		boolean again = true;
		while (again) {
			System.out.println("What connection of characters are you interested in?");
			System.out.print("Character one: ");
			String char1 = console.nextLine().trim();
			System.out.print("Character two: ");
			String char2 = console.nextLine().trim();
			
			List<String> path = mp.search(char1, char2);
			// print out the path it returns
			
			System.out.print("Want to search more? (Y/N) ");
			String ans = console.nextLine();
			if (ans.toUpperCase().startsWith("N")) {
				again = false;
				System.out.println("Have a nice day! :D");
			}
			System.out.println();
		}
	}
	
	/**
	 * Checks the representation invariant holds.
	 */
	private void checkRep() {
		
	}
}
