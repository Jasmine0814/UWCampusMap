package graph.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.rules.Timeout;

public class testNode {

    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private static DirectedLabeledGraph.Node Nempty = new DirectedLabeledGraph.Node("");
    private static DirectedLabeledGraph.Node Na = new DirectedLabeledGraph.Node("a");
    private static DirectedLabeledGraph.Node Na1 = new DirectedLabeledGraph.Node("a");
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
        assertEquals("",Nempty.getData());
        assertEquals("a",Na.getData());
        assertEquals("A",NA.getData());
        assertEquals("b",Nb.getData());
        assertEquals("123",number.getData());
    }

    @Test
    public void testEquals() {
        assertEquals(Na, Na1);
        assertNotEquals(Na, Nb);
    }

    @Test
    public void testHashCode() {
        assertEquals(Na, Na1);
        assertNotEquals(Na, Nb);
    }
}
