package graph;

import java.util.Set;

/**
 * This class represent a mutable Directed Labeled Graph(DLG) which is a
 * collection of nodes and edges. Each edge connects two nodes with directed
 * label. e.g. e(A,B) means the edge from A to B, B is the child node and A
 * is the parent node. both Node and Edge are unique which means node is different
 * from others and can have multiple edges from node A to node B as long as they
 * have unique labels.
 */

public class DirectedLabeledGraph {

    /**
     * this is constructor that construct new empty DLG
     * @spec.modifies this
     * @spec.effects create a new empty DLG
     */
    public void DirectedLabeledGraph() {
        throw new RuntimeException("constructor is not yet implemented");
    }

    /**
     * add the given Edge to this graph
     * @param e a unique edge that different from other
     * @spec.modifies this
     * @spec.effects add a edge to listNodes corresponding to parent and child node
     */
    public void addEdge(Edge e) {
        throw new RuntimeException("addEdges method is not yet implemented");
    }

    /**
     * add node to this graph
     * @param n a unique node that different from other
     * @spec.modifies this
     * @spec.effects add a Node to this DirectedLabeledGraph(DLG)
     */
    public void addNode(Node n) {
        throw new RuntimeException("addNode method is not yet implemented");
    }

    /**
     * return A set of node that all in this DLG
     * @return A set of all Nodes in this DLG
     */
    public Set<Node> listNodes() {
        throw new RuntimeException("listNodes method is not yet implemented");
    }

    /**
     * return a set of Edges between the given parent and its children
     * @param parent the parent node that need to find children
     * @return a set of all edges from the given parent node
     * @spec.requires parent cannot be null
     */
    public Set<Edge> getChildren(Node parent) {
        throw new RuntimeException("getChildren method is not yet implemented");
    }

    /**
     * This class represent an immutable edge in DLG from parent node to child node
     * and labeled by a String
     */
    public static class Edge {

        /**
         * this is a constructor that construct an edge with parent, child and label
         * @param parent represent the start point of the edge
         * @param child represent the end point of the edge
         * @param label the weight of the edge
         * @spec.modifies parent, child, label
         * @spec.effects create a new Edge with given parent, child and label.
         * @spec.requires parent, child, label cannot be null
         */

        public Edge(Node parent, Node child, String label) {
            throw new RuntimeException("the constructor is not yet implemented");
        }

        /**
         * return the node that is the parent of this edge
         * @return the parent node of this edge
         */
        public Node getParent() {
            throw new RuntimeException("getParent method is not yet implemented");
        }

        /**
         * return the node that is the child of this edge
         * @return the child node of this edge
         */
        public Node getChild() {
            throw new RuntimeException("getChild method is not yet implemented");
        }

        /**
         * return the label of this edge
         * @return the label of this edge
         */
        public String getLabel() {
            throw new RuntimeException("getLabel method is not yet implemented");
        }
    }

    /**
     * This class represents an immutable node of this DLG, containing string data
     */
    public static class Node {

        /**
         * this is a constructor that construct a node with given data
         * @param data the data that store in the node
         * @spec.modifies this.data
         * @spec.effects create new node with given data
         */
        public Node(String data) {
            throw new RuntimeException("this constructor is not yet implemented");
        }

        /**
         * return the data of this node
         * @return the data of this node
         */
        public String getData() {
            throw new RuntimeException("getData method is not yet implemented");
        }

    }
}
