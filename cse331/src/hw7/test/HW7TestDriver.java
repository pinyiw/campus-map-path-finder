package hw7.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import hw5.Graph;
import hw5.GraphNode;
import hw6.MarvelParser.MalformedDataException;
import hw7.GraphNodePath;
import hw7.MarvelPaths2;


/**
 * This class implements a testing driver which reads test scripts
 * from files for your graph ADT and improved MarvelPaths application
 * using Dijkstra's algorithm.
 **/
public class HW7TestDriver {


    public static void main(String args[]) {
    	try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW7TestDriver td;

            if (args.length == 0) {
                td = new HW7TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW7TestDriver(new FileReader(tests),
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
        System.err.println("to read from a file: java hw7.test.HW7TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw7.test.HW7TestDriver");
    }
    
    private final PrintWriter output;
    private final BufferedReader input;
    private final Map<String, Graph<String, Double>> graphs = new HashMap<String, Graph<String, Double	>>();
    
    public HW7TestDriver(Reader r, Writer w) {
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
  	  Graph<String, Double> graph = MarvelPaths2.loadGraph("src/hw7/data/" + file);
  	  graphs.put(graphName, graph);
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
  	  Graph<String, Double> g = graphs.get(graphName);
  	  GraphNode<String> node_1 = new GraphNode<String>(node1);
  	  GraphNode<String> node_2 = new GraphNode<String>(node2);
  	  if (!g.contains(node_1) || !g.contains(node_2)) {
  		  if (!g.contains(new GraphNode<String>(node1))) {
  			  output.println("unknown character " + node1);
  		  }
  		  if (!g.contains(new GraphNode<String>(node2))) {
  			  output.println("unknown character " + node2);
  		  }
  	  } else {
  		  GraphNodePath<String> gnp = MarvelPaths2.findPath(g, node1, node2);
  		  List<GraphNode<String>> path = gnp.getPath();
  		  output.println("path from " + node1 + " to " + node2 + ":");
  		  if (path == null) {
  			  output.println("no path found");
  		  } else {
  			  if (path.size() == 1) {
  				  path.clear();
  			  }
  			  Double weight = 0.0;
  			  for (int i = 0; i < path.size() - 1; i++) {
  				  GraphNode<String> first = path.get(i);
  				  GraphNode<String> second = path.get(i + 1);
  				  output.print(first.getName() + " to " + 
  						  	   second.getName() + " with weight ");
  				  List<Double> weightList = g.getEdgeData(first, second);
  				  Collections.sort(weightList);
  				  weight += weightList.get(0);
  				  output.println(String.format("%.3f", weightList.get(0)));
  			  }
  			  output.println(String.format("total cost: %.3f", weight));
  		  }
//  		  MarvelPaths mp = new MarvelPaths(g);
//  		  List<String> path = mp.search(node1, node2);
//  		  output.println("path from " + node1 + " to " + node2 + ":");
//  		  if (path == null) {
//  			  output.println("no path found");
//  		  } else if (!path.isEmpty()) {
//  			  output.println(node1 + " to " + path.get(0) + " via " +
//  					  											path.get(1));
//  			  for (int i = 2; i < path.size() - 1; i += 2) {
//  				  output.println(path.get(i - 2) + " to " + path.get(i) + 
//  						  			" via " + path.get(i + 1));
//  			  }
//  		  }
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
    	Graph<String, Double> g = new Graph<String, Double>();
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
    	Graph<String, Double> g = graphs.get(graphName);
    	g.addNode(new GraphNode<String>(nodeName));
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
    	Graph<String, Double> g = graphs.get(graphName);
    	Double weight = Double.parseDouble(edgeLabel);
    	g.addEdge(new GraphNode<String>(parentName), new GraphNode<String>(childName),
    							weight);
    	output.println(String.format("added edge %.3f from " + parentName +
    					" to " + childName + " in " + graphName, weight));
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
    	Graph<String, Double> g = graphs.get(graphName);
    	Set<GraphNode<String>> set = g.nodes();
    	String[] arr = new String[set.size()];
    	int count = 0;
    	for (GraphNode<String> node: set) {
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
    	Graph<String, Double> g = graphs.get(graphName);
    	Set<GraphNode<String>> set = g.childNode(new GraphNode<String>(parentName));
    	String[] arr = new String[set.size()];
    	int count = 0;
    	for (GraphNode<String> node: set) {
    		arr[count] = node.getName();
    		count++;
    	}
    	Arrays.sort(arr);
    	for (int i = 0; i < arr.length; i++) {
    		String cur = arr[i];
    		List<Double> list = g.getEdgeData(new GraphNode<String>(parentName),
    											new GraphNode<String>(arr[i]));
    		Collections.sort(list);
    		arr[i] += String.format("(%.3f)", list.get(0));
    		for (int j = 1; j < list.size(); j++) {
    			arr[i] += String.format(" " + cur + "(%.3f)", list.get(j)); 
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
