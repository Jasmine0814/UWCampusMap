package graph.junitTests;

import graph.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.rules.Timeout;

public class TestEdge {

    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private static DirectedLabeledGraph.Node<String> Nempty = new DirectedLabeledGraph.Node<String>("");
    private static DirectedLabeledGraph.Node<String> Na = new DirectedLabeledGraph.Node<String>("a");
    private static DirectedLabeledGraph.Node<String> Na2 = new DirectedLabeledGraph.Node<String>("a");

    private static DirectedLabeledGraph.Node<String> NA = new DirectedLabeledGraph.Node<String>("A");
    private static DirectedLabeledGraph.Node<String> Nb = new DirectedLabeledGraph.Node<String>("b");

    private static DirectedLabeledGraph.Edge<String,String> EaTob = new DirectedLabeledGraph.Edge<>(Na, Nb, "EaTob");

    private static DirectedLabeledGraph.Edge<String,String> EbToa = new DirectedLabeledGraph.Edge<>(Nb, Na, "EbToa");
    private static DirectedLabeledGraph.Edge<String,String> EbToa1 = new DirectedLabeledGraph.Edge<>(Nb, Na, "EbToa");

    private static DirectedLabeledGraph.Edge<String,String> EaToA = new DirectedLabeledGraph.Edge<>(Na, NA, "EaToA");
    private static DirectedLabeledGraph.Edge<String,String> EaTob1 = new DirectedLabeledGraph.Edge<>(Na, Nb, "EaTob1");
    private static DirectedLabeledGraph.Edge<String,String> EselfPointing = new DirectedLabeledGraph.Edge<>(Na, Na, "EselfPointing");
    private static DirectedLabeledGraph.Edge<String,String> EemptySelfPointing = new DirectedLabeledGraph.Edge<>(Nempty, Nempty, "EemptySelfPointing");

    @Test
    public void testCreateEdge() {
        DirectedLabeledGraph.Edge<String,String> EaTob = new DirectedLabeledGraph.Edge<>(Na, Nb, "aTob");
        DirectedLabeledGraph.Edge<String,String> EbToa = new DirectedLabeledGraph.Edge<>(Nb, Na, "bToa");
        DirectedLabeledGraph.Edge<String,String> EaToA = new DirectedLabeledGraph.Edge<>(Na, NA, "aToA");
        DirectedLabeledGraph.Edge<String,String> EaTob1 = new DirectedLabeledGraph.Edge<>(Na, Nb, "aTob1");
        DirectedLabeledGraph.Edge<String,String> EselfPointing = new DirectedLabeledGraph.Edge<>(Na, Na, "selfPointing");
        DirectedLabeledGraph.Edge<String,String> EemptySelfPointing = new DirectedLabeledGraph.Edge<>(Nempty, Nempty, "emptySelfPointing");
    }

    @Test
    public void testgetParent() {
        assertEquals("a",EaTob.getParent().getData());
        assertEquals("b",EbToa.getParent().getData());
        assertEquals("a",EaToA.getParent().getData());
        assertEquals("a",EselfPointing.getParent().getData());
        assertEquals("",EemptySelfPointing.getParent().getData());
    }

    @Test
    public void testgetChildren() {
        assertEquals("b",EaTob.getChild().getData());
        assertEquals("a",EbToa.getChild().getData());
        assertEquals("A",EaToA.getChild().getData());
        assertEquals("a",EselfPointing.getChild().getData());
        assertEquals("",EemptySelfPointing.getChild().getData());
    }

    @Test
    public void testgetLabel() {
        assertEquals("EaTob",EaTob.getLabel());
        assertEquals("EbToa",EbToa.getLabel());
        assertEquals("EaToA",EaToA.getLabel());
        assertEquals("EaTob1",EaTob1.getLabel());
        assertEquals("EselfPointing",EselfPointing.getLabel());
        assertEquals("EemptySelfPointing",EemptySelfPointing.getLabel());

    }

    @Test
    public void testEquals() {
        assertEquals(EbToa,EbToa1);
        assertNotEquals(EaTob,EbToa);
    }

    @Test
    public void testHashCode() {
        assertEquals(EbToa,EbToa1);
        assertNotEquals(EaTob,EbToa);
    }

}
