package graph.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class testNode {
    private static DirectedLabeledGraph.Node Nempty = new DirectedLabeledGraph.Node("");
    private static DirectedLabeledGraph.Node Na = new DirectedLabeledGraph.Node("a");
    private static DirectedLabeledGraph.Node NA = new DirectedLabeledGraph.Node("A");
    private static DirectedLabeledGraph.Node Nb = new DirectedLabeledGraph.Node("b");
    private static DirectedLabeledGraph.Node number = new DirectedLabeledGraph.Node("123");

    @Test
    public void testCreateNode() {
        new DirectedLabeledGraph.Node("");
        new DirectedLabeledGraph.Node("a");
        new DirectedLabeledGraph.Node("A");
        new DirectedLabeledGraph.Node("b");
        new DirectedLabeledGraph.Node("123");
    }

    @Test
    public void testGetData() {
        assertEquals("Nempty",Nempty.getData());
        assertEquals("Na",Na.getData());
        assertEquals("NA",NA.getData());
        assertEquals("Nb",Nb.getData());
        assertEquals("number",number.getData());
    }
}
