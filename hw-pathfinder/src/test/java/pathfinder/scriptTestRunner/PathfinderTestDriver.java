/*
 * Copyright (C) 2022 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Summer Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.DirectedLabeledGraph;
import pathfinder.Dijkstra;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {
    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, DirectedLabeledGraph<String,Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);        // See GraphTestDriver as an example.
    }

    // Leave this method public
    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        DirectedLabeledGraph<String,Double> dlg = new DirectedLabeledGraph<>();
        graphs.put(graphName, dlg);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DirectedLabeledGraph<String, Double> dlg = graphs.get(graphName);
        DirectedLabeledGraph.Node<String> newNode = new DirectedLabeledGraph.Node<String>(nodeName);
        dlg.addNode(newNode);
        output.println("added node " + nodeName + " to " + graphName);

        // ___ = graphs.get(graphName);
        // output.println(...);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        double eLabel = Double.parseDouble(edgeLabel);
        DirectedLabeledGraph<String,Double> dlg = graphs.get(graphName);
        DirectedLabeledGraph.Edge<String,Double> newEdge = new DirectedLabeledGraph.Edge<>(
                new DirectedLabeledGraph.Node<String>(parentName),new DirectedLabeledGraph.Node<String>(childName),eLabel);
        dlg.addEdge(newEdge);
        output.println("added edge " + String.format("%.3f",eLabel) + " from " + parentName + " to "
                + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        DirectedLabeledGraph<String,Double> dlg = graphs.get(graphName);
        output.print(graphName + " contains:");
        List<DirectedLabeledGraph.Node<String>> allNodes =  new ArrayList<>(dlg.listNodes());
        for(DirectedLabeledGraph.Node<String> n : allNodes) {
            output.print(" " + n.getData());
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        DirectedLabeledGraph<String,Double> dlg = graphs.get(graphName);
        DirectedLabeledGraph.Node<String> parent = new DirectedLabeledGraph.Node<>(parentName);
        output.print("the children of " + parentName + " in " + graphName + " are:");
        List<DirectedLabeledGraph.Edge<String,Double>> edges =  new ArrayList<>(dlg.getOutgoingEdges(parent));
        Collections.sort(edges, new Comparator<>() {
            public int compare(DirectedLabeledGraph.Edge<String,Double> a, DirectedLabeledGraph.Edge<String,Double> b) {
                int childComp = a.getChild().getData().compareTo(b.getChild().getData());
                if(childComp == 0) {
                    return a.getLabel().compareTo(b.getLabel());
                } else {
                    return childComp;
                }
            }
        });

        for(DirectedLabeledGraph.Edge<String,Double> e : edges) {
            output.print(" " + e.getChild() + "(" + e.getLabel() + ")");
        }
        output.println();
    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String dest = arguments.get(2);
        findPath(graphName, start, dest);
    }

    private void findPath(String graphName, String start,String dest) {
        DirectedLabeledGraph<String, Double> dlg = graphs.get(graphName);
        DirectedLabeledGraph.Node<String> startNode = new DirectedLabeledGraph.Node<>(start);
        DirectedLabeledGraph.Node<String> destNode = new DirectedLabeledGraph.Node<>(dest);

        if(!dlg.containsNode(startNode) || !dlg.containsNode(destNode)) {
            if (!dlg.containsNode(startNode)) {
                output.println("unknown: " + start);
            }
            if (!dlg.containsNode(destNode)) {
                output.println("unknown: " + dest);
            }
        } else {
            output.println("path from " + start + " to " + dest + ":");
            Path<String> path = Dijkstra.pathFinder(dlg, startNode, destNode);
            if (path == null) {
                output.println("no path found");
            } else {
                Iterator<Path<String>.Segment> iter = path.iterator();
                //double sum = 0.0;
                while (iter.hasNext()) {
                    Path<String>.Segment temp = iter.next();
                    output.println(temp.getStart() + " to " +
                            temp.getEnd() + " with " + String.format("weight %.3f", temp.getCost()));
                }
                output.println("total cost:" + String.format(" %.3f", path.getCost()));
            }
        }
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
