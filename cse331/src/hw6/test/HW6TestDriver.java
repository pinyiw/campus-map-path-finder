package hw6.test;

import java.io.*;
import java.util.*;

import hw5.Graph;
import hw5.GraphNode;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;


/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph, the Marvel parser, and your BFS
 * algorithm.
 **/
public class HW6TestDriver {


  public static void main(String args[]) {
	  try {
          if (args.length > 1) {
              printUsage();
              return;
          }

          HW6TestDriver td;

          if (args.length == 0) {
              td = new HW6TestDriver(new InputStreamReader(System.in),
                                     new OutputStreamWriter(System.out));
          } else {

              String fileName = args[0];
              File tests = new File (fileName);

              if (tests.exists() || tests.canRead()) {
                  td = new HW6TestDriver(new FileReader(tests),
                                         new OutputStreamWriter(System.out));
              } else {
                  System.err.println("Cannot read from " + tests.toString());
                  printUsage();
                  return;
              }
          }

          td.runTests();

      } catch (IOException e) {
          System.err.println(e.toString());
          e.printStackTrace(System.err);
      }
  }
  
  private static void printUsage() {
      System.err.println("Usage:");
      System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
      System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
  }
  
  private final PrintWriter output;
  private final BufferedReader input;
  private final Map<String, Graph> graphs = new HashMap<String, Graph>();
  
  public HW6TestDriver(Reader r, Writer w) {
	  input = new BufferedReader(r);
	  output = new PrintWriter(w);
  }

  public void runTests() throws IOException {
	  String inputLine;
      while ((inputLine = input.readLine()) != null) {
          if ((inputLine.trim().length() == 0) ||
              (inputLine.charAt(0) == '#')) {
              // echo blank and comment lines
              output.println(inputLine);
          }
          else
          {
              // separate the input line on white space
              StringTokenizer st = new StringTokenizer(inputLine);
              if (st.hasMoreTokens()) {
                  String command = st.nextToken();

                  List<String> arguments = new ArrayList<String>();
                  while (st.hasMoreTokens()) {
                      arguments.add(st.nextToken().replace('_', ' '));
                  }

                  executeCommand(command, arguments);
              }
          }
          output.flush();
      }
  }
  
  private void executeCommand(String command, List<String> arguments) {
	  try {
          if (command.equals("CreateGraph")) {
              createGraph(arguments);
          } else if (command.equals("AddNode")) {
              addNode(arguments);
          } else if (command.equals("AddEdge")) {
              addEdge(arguments);
          } else if (command.equals("ListNodes")) {
              listNodes(arguments);
          } else if (command.equals("ListChildren")) {
              listChildren(arguments);
          } else if (command.equals("LoadGraph")) {
        	  loadGraph(arguments);
          } else if (command.equals("FindPath")) {
        	  findPath(arguments);
          } else {
              output.println("Unrecognized command: " + command);
          }
      } catch (Exception e) {
          output.println("Exception: " + e.toString());
      }
  }
  
  private void loadGraph(List<String> arguments) throws MalformedDataException {
	  if (arguments.size() != 2) {
		  throw new CommandException("Bad arguments to LoadGraph: " + arguments);
	  }
	  
	  String graphName = arguments.get(0);
	  String file = arguments.get(1);
	  loadGraph(graphName, file);
  }
  
  private void loadGraph(String graphName, String file) throws MalformedDataException {
	  MarvelPaths mp = new MarvelPaths("src/hw6/data/" + file);
	  graphs.put(graphName, mp.graph);
	  output.println("loaded graph " + graphName);
  }
  
  private void findPath(List<String> arguments) {
	  if (arguments.size() != 3) {
		  throw new CommandException("Bad arguments to FindPath: " + arguments);
	  }
	  
	  String graphName = arguments.get(0);
	  String node1 = arguments.get(1);
	  String node2 = arguments.get(2);
	  findPath(graphName, node1, node2);
  }
  
  private void findPath(String graphName, String node1, String node2) {
	  Graph g = graphs.get(graphName);
	  GraphNode node_1 = new GraphNode(node1);
	  GraphNode node_2 = new GraphNode(node2);
	  if (!g.contains(node_1) || !g.contains(node_2)) {
		  if (!g.contains(new GraphNode(node1))) {
			  output.println("unknown character " + node1);
		  }
		  if (!g.contains(new GraphNode(node2))) {
			  output.println("unknown character " + node2);
		  }
	  } else {
		  MarvelPaths mp = new MarvelPaths(g);
		  List<String> path = mp.search(node1, node2);
		  output.println("path from " + node1 + " to " + node2 + ":");
		  if (path == null) {
			  output.println("no path found");
		  } else if (!path.isEmpty()) {
			  output.println(node1 + " to " + path.get(0) + " via " +
					  											path.get(1));
			  for (int i = 2; i < path.size() - 1; i += 2) {
				  output.println(path.get(i - 2) + " to " + path.get(i) + 
						  			" via " + path.get(i + 1));
			  }
		  }
	  }
  }
  
  private void createGraph(List<String> arguments) {
      if (arguments.size() != 1) {
          throw new CommandException("Bad arguments to CreateGraph: " + arguments);
      }

      String graphName = arguments.get(0);
      createGraph(graphName);
  }

  private void createGraph(String graphName) {
      // Insert your code here.

      // graphs.put(graphName, ___);
      // output.println(...);
  	Graph g = new Graph();
  	graphs.put(graphName, g);
  	output.println("created graph " + graphName);
  }

  private void addNode(List<String> arguments) {
      if (arguments.size() != 2) {
          throw new CommandException("Bad arguments to addNode: " + arguments);
      }

      String graphName = arguments.get(0);
      String nodeName = arguments.get(1);

      addNode(graphName, nodeName);
  }

  private void addNode(String graphName, String nodeName) {
      // Insert your code here.

      // ___ = graphs.get(graphName);
      // output.println(...);
  	Graph g = graphs.get(graphName);
  	g.addNode(new GraphNode(nodeName));
  	output.println("added node " + nodeName + " to " + graphName);
  }

  private void addEdge(List<String> arguments) {
      if (arguments.size() != 4) {
          throw new CommandException("Bad arguments to addEdge: " + arguments);
      }

      String graphName = arguments.get(0);
      String parentName = arguments.get(1);
      String childName = arguments.get(2);
      String edgeLabel = arguments.get(3);

      addEdge(graphName, parentName, childName, edgeLabel);
  }

  private void addEdge(String graphName, String parentName, String childName,
          String edgeLabel) {
      // Insert your code here.

      // ___ = graphs.get(graphName);
      // output.println(...);
  	Graph g = graphs.get(graphName);
  	g.addEdge(new GraphNode(parentName), new GraphNode(childName), edgeLabel);
  	output.println("added edge " + edgeLabel + " from " + parentName +
  					" to " + childName + " in " + graphName);
  }

  private void listNodes(List<String> arguments) {
      if (arguments.size() != 1) {
          throw new CommandException("Bad arguments to listNodes: " + arguments);
      }

      String graphName = arguments.get(0);
      listNodes(graphName);
  }

  private void listNodes(String graphName) {
      // Insert your code here.

      // ___ = graphs.get(graphName);
      // output.println(...);
  	Graph g = graphs.get(graphName);
  	Set<GraphNode> set = g.nodes();
  	String[] arr = new String[set.size()];
  	int count = 0;
  	for (GraphNode node: set) {
  		arr[count] = node.getName();
  		count++;
  	}
  	Arrays.sort(arr);
  	output.print(graphName + " contains:");
  	for (int i = 0; i < arr.length; i++) {
  		output.print(" " + arr[i]);
  	}
  	output.println();
  }

  private void listChildren(List<String> arguments) {
      if (arguments.size() != 2) {
          throw new CommandException("Bad arguments to listChildren: " + arguments);
      }

      String graphName = arguments.get(0);
      String parentName = arguments.get(1);
      listChildren(graphName, parentName);
  }

  private void listChildren(String graphName, String parentName) {
      // Insert your code here.

      // ___ = graphs.get(graphName);
      // output.println(...);
  	Graph g = graphs.get(graphName);
  	Set<GraphNode> set = g.childNode(new GraphNode(parentName));
  	String[] arr = new String[set.size()];
  	int count = 0;
  	for (GraphNode node: set) {
  		arr[count] = node.getName();
  		count++;
  	}
  	Arrays.sort(arr);
  	for (int i = 0; i < arr.length; i++) {
  		String cur = arr[i];
  		List<String> list = g.getEdgeData(new GraphNode(parentName),
  											new GraphNode(arr[i]));
  		Collections.sort(list);
  		arr[i] += "(" + list.get(0) + ")";
  		for (int j = 1; j < list.size(); j++) {
  			arr[i] += " " + cur + "(" + list.get(j) + ")"; 
  		}
  	}
  	output.print("the children of " + parentName + " in " + graphName +
  					" are:");
  	for (int i = 0; i < arr.length; i++) {
  		output.print(" " + arr[i]);
  	}
  	output.println();
  }

  /**
   * This exception results when the input file cannot be parsed properly
   **/
  static class CommandException extends RuntimeException {

      public CommandException() {
          super();
      }
      public CommandException(String s) {
          super(s);
      }

      public static final long serialVersionUID = 3495;
  }
}
