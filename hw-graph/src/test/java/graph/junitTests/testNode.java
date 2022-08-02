package graph.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.rules.Timeout;

public class testNode {

    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private static DirectedLabeledGraph.Node<String> Nempty = new DirectedLabeledGraph.Node<String>("");
    private static DirectedLabeledGraph.Node<String> Na = new DirectedLabeledGraph.Node<String>("a");
    private static DirectedLabeledGraph.Node<String> Na1 = new DirectedLabeledGraph.Node<String>("a");
    private static DirectedLabeledGraph.Node<String> NA = new DirectedLabeledGraph.Node<String>("A");
    private static DirectedLabeledGraph.Node<String> Nb = new DirectedLabeledGraph.Node<String>("b");
    private static DirectedLabeledGraph.Node<String> number = new DirectedLabeledGraph.Node<String>("123");

    @Test
    public void testCreateNode() {
        new DirectedLabeledGraph.Node<>("");
        new DirectedLabeledGraph.Node<>("a");
        new DirectedLabeledGraph.Node<>("A");
        new DirectedLabeledGraph.Node<>("b");
        new DirectedLabeledGraph.Node<>("123");
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
