package graph;

import java.util.*;

/**
 * This class represent a mutable Directed Labeled Graph(DLG) which is a
 * collection of nodes and edges. Each edge connects two nodes with directed
 * label. e.g. e(A,B) means the edge from A to B, B is the child node and A
 * is the parent node. both Node and Edge are unique which means node is different
 * from others and can have multiple edges from node A to node B as long as they
 * have unique labels.
 */

public class DirectedLabeledGraph<T,E> {

    private Map<Node<T>, Set<Edge<T,E>>> graph;
    public static final boolean DEBUG = false;


    // RI: list != null and each term in the map is not null.
    // AF:
    // AF(this) = A Directed Labeled Graph g, g.elements = this.graph
    // All nodes in Graph = this.g.keySet()
    // All Edges starting at Node n = this.g.getKey(n)
    /**
     * this is constructor that construct new empty DLG
     * @spec.modifies this
     * @spec.effects create a new empty DLG
     */
    public DirectedLabeledGraph() {
        this.graph = new HashMap<>();
        if(DEBUG) {
            checkRep();
        }
    }

    /**
     * add the given Edge to this graph
     * @param e a unique edge that different from other
     * @spec.modifies this
     * @spec.effects add a edge to listNodes corresponding to parent and child node
     */
    public void addEdge(Edge<T,E> e) {
        if(DEBUG) {
            checkRep();
        }
        if(!graph.keySet().contains(e.getChild()) ) {
            addNode(e.getChild());
        }
        if(!graph.keySet().contains(e.getParent())) {
            addNode(e.getParent());
        }

        Set<Edge<T,E>> edgesSet = graph.get(e.getParent());
        edgesSet.add(e);
        graph.put(e.getParent(), edgesSet);
        if(DEBUG) {
            checkRep();
        }
    }

    /**
     * add node to this graph
     * @param n a unique node that different from other
     * @spec.modifies this
     * @spec.effects add a Node to this DirectedLabeledGraph(DLG)
     */
    public void addNode(Node<T> n) {
        if(DEBUG) {
            checkRep();
        }
        if(!graph.keySet().contains(n)) {
            graph.put(n, new HashSet<>());
        }
        if(DEBUG) {
            checkRep();
        }
    }

    /**
     * return A set of node that all in this DLG
     * @return A set of all Nodes in this DLG
     */
    public Set<Node<T>> listNodes() {
        if(DEBUG) {
            checkRep();
        }
        Set<Node<T>> allNodes = Collections.unmodifiableSet(graph.keySet());
        if(DEBUG) {
            checkRep();
        }
        return allNodes;
    }

    /**
     * return a set of Edges between the given parent and its children
     * @param parent the parent node that need to find children
     * @return a set of all edges from the given parent node
     * @spec.requires parent cannot be null
     */
    public Set<Edge<T,E>> getChildren(Node parent) {
        if(DEBUG) {
            checkRep();
        }
        Set<Edge<T,E>> edges = Collections.unmodifiableSet(graph.get(parent));
        if(DEBUG) {
            checkRep();
        }
        return edges;
    }

    private void checkRep() {
        if(graph == null) {
            throw new RuntimeException("The graph is null");
        }
        for(Node n : graph.keySet()) {
            if(n == null) {
                throw new RuntimeException("the node is null");
            }
            if(graph.get(n) == null) {
                throw new RuntimeException("the set of edge is null");
            }
            for(Edge e : graph.get(n)) {
                if(e == null) {
                    throw new RuntimeException("the edge is null");
                }
            }
        }
    }



    /**
     * This class represent an immutable edge in DLG from parent node to child node
     * and labeled by a String
     */
    public static class Edge<T,E> {

        // RI: parent != null and child != null and label != null
        // AF: AF(this) = A Edge e
        //      this.parent == e.parent and
        //      this.child == e.child and
        //      this.label == e.label

        private final Node<T> parent;
        private final Node<T> child;
        private final E label;

        /**
         * this is a constructor that construct an edge with parent, child and label
         * @param parent represent the start point of the edge
         * @param child represent the end point of the edge
         * @param label the weight of the edge
         * @spec.modifies parent, child, label
         * @spec.effects create a new Edge with given parent, child and label.
         * @spec.requires parent, child, label cannot be null
         */

        public Edge(Node<T> parent, Node<T> child, E label) {
            this.parent = parent;
            this.child = child;
            this.label = label;
            checkRep();
        }

        /**
         * return the node that is the parent of this edge
         * @return the parent node of this edge
         */
        public Node<T> getParent() {
            return this.parent;
        }

        /**
         * return the node that is the child of this edge
         * @return the child node of this edge
         */
        public Node<T> getChild() {
            return this.child;
        }

        /**
         * return the label of this edge
         * @return the label of this edge
         */
        public E getLabel() {
            return this.label;
        }

        private void checkRep() {
            if(this.parent == null) {
                throw new RuntimeException("the parent of edge is null");
            }
            if(this.child == null) {
                throw new RuntimeException("the child of edge is null");
            }
            if(this.label == null) {
                throw new RuntimeException("the label of edge is null");
            }
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Edge<?,?>) {
                Edge<?,?> n = (Edge<?,?>) obj;
                return (this.label.equals(n.label) &&
                        this.parent.equals(n.parent) &&
                        this.child.equals(n.child));
            } else {
                return false;
            }
        }
    }

    /**
     * This class represents an immutable node of this DLG, containing string data
     */
    public static class Node<T> {

        // RI: data of node is not null
        // AF: AF(this) = a Node n
        //      this.data = n.data
        private final T data;
        /**
         * this is a constructor that construct a node with given data
         * @param data the data that store in the node
         * @spec.modifies this.data
         * @spec.effects create new node with given data
         */
        public Node(T data) {
            this.data = data;
            checkRep();
        }

        /**
         * return the data of this node
         * @return the data of this node
         */
        public T getData() {
            return data;
        }

        private void checkRep() {
            if(data == null) {
                throw new RuntimeException("the data is null");
            }
        }
        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Node<?>)) {
                return false;
            } else {
                Node<?> n = (Node<?>) obj;
                return(this.data.equals(n.data));
            }
        }

        public int hashCode() {
            return this.data.hashCode();
        }
        @Override
        public String toString(){
            return data.toString();
        }
    }
}
