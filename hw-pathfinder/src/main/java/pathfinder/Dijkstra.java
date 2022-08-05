package pathfinder;

import graph.DirectedLabeledGraph;
import pathfinder.datastructures.Path;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra {

    public static <P> Path<P> pathFinder(DirectedLabeledGraph<P,Double> dlg, DirectedLabeledGraph.Node<P> startNode,
                                         DirectedLabeledGraph.Node<P> destNode) {

        assert (dlg != null) : "the dlg should not be null";
        assert (startNode != null) : "the start node should not be null";
        assert (destNode != null) : "the end node should not be null";

        assert (dlg.containsNode(startNode)) : "start node is not in the dlg";
        assert (dlg.containsNode(destNode)) : "dest node is not in the dlg";

        PriorityQueue<Path<P>> active = new PriorityQueue<>(new Comparator<Path<P>>() {
            @Override
            public int compare(Path<P> p1, Path<P> p2) {
                return Double.compare(p1.getCost(), p2.getCost());
            }
        });
        active.add(new Path<>(startNode.getData()));

        // Each element is a path from start to a given node.
        // A path's “priority” in the queue is the total cost of that path.
        // Nodes for which no path is known yet are not in the queue.
        Set<DirectedLabeledGraph.Node<P>> finished = new HashSet<>();

        while (!active.isEmpty()) {
            Path<P> minPath = active.poll();
            DirectedLabeledGraph.Node<P> minDest = new DirectedLabeledGraph.Node<>(minPath.getEnd());
            if(minDest.equals(destNode)){
                return minPath; //find the min
            }
            if(!finished.contains(minDest)) {
                for(DirectedLabeledGraph.Edge<P,Double> edge : dlg.getOutgoingEdges(minDest)){ // tranverse all edges starting from minDest
                    // If we don't know the minimum-cost path from start to child,
                    // examine the path we've just found
                    if(edge != null && !finished.contains(edge.getChild())) {
                        Path<P> newPath = minPath.extend(edge.getChild().getData(),edge.getLabel());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
        return null;
    }
}
