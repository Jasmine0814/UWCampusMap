package graph.junitTests;

import graph.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class testEdge {

    private static DirectedLabeledGraph.Node Nempty = new DirectedLabeledGraph.Node("");
    private static DirectedLabeledGraph.Node Na = new DirectedLabeledGraph.Node("a");
    private static DirectedLabeledGraph.Node NA = new DirectedLabeledGraph.Node("A");
    private static DirectedLabeledGraph.Node Nb = new DirectedLabeledGraph.Node("b");

    private static DirectedLabeledGraph.Edge EaTob = new DirectedLabeledGraph.Edge(Na, Nb, "aTob");
    private static DirectedLabeledGraph.Edge EbToa = new DirectedLabeledGraph.Edge(Nb, Na, "bToa");
    private static DirectedLabeledGraph.Edge EaToA = new DirectedLabeledGraph.Edge(Na, NA, "aToA");
    private static DirectedLabeledGraph.Edge EaTob1 = new DirectedLabeledGraph.Edge(Na, Nb, "aTob1");
    private static DirectedLabeledGraph.Edge EselfPointing = new DirectedLabeledGraph.Edge(Na, Na, "selfPointing");
    private static DirectedLabeledGraph.Edge EemptySelfPointing = new DirectedLabeledGraph.Edge(Nempty, Nempty, "emptySelfPointing");

    @Test
    public void testCreateEdge() {
        DirectedLabeledGraph.Edge EaTob = new DirectedLabeledGraph.Edge(Na, Nb, "aTob");
        DirectedLabeledGraph.Edge EbToa = new DirectedLabeledGraph.Edge(Nb, Na, "bToa");
        DirectedLabeledGraph.Edge EaToA = new DirectedLabeledGraph.Edge(Na, NA, "aToA");
        DirectedLabeledGraph.Edge EaTob1 = new DirectedLabeledGraph.Edge(Na, Nb, "aTob1");
        DirectedLabeledGraph.Edge EselfPointing = new DirectedLabeledGraph.Edge(Na, Na, "selfPointing");
        DirectedLabeledGraph.Edge EemptySelfPointing = new DirectedLabeledGraph.Edge(Nempty, Nempty, "emptySelfPointing");
    }

    @Test
    public void testgetParent() {
        assertEquals("Na",EaTob.getParent().getData());
        assertEquals("Nb",EbToa.getParent().getData());
        assertEquals("Na",EaToA.getParent().getData());
        assertEquals("Na",EselfPointing.getParent().getData());
        assertEquals("Nempty",EemptySelfPointing.getParent().getData());
    }

    public void testgetChildren() {
        assertEquals("Nb",EaTob.getChild().getData());
        assertEquals("Na",EbToa.getChild().getData());
        assertEquals("NA",EaToA.getChild().getData());
        assertEquals("Na",EselfPointing.getChild().getData());
        assertEquals("Nempty",EemptySelfPointing.getChild().getData());
    }

    public void testgetLabel() {
        assertEquals("EaTob",EaTob.getLabel());
        assertEquals("EbToa",EbToa.getLabel());
        assertEquals("EaToA",EaToA.getLabel());
        assertEquals("EaTob1",EaTob1.getLabel());
        assertEquals("EselfPointing",EselfPointing.getLabel());
        assertEquals("EemptySelfPointing",EemptySelfPointing.getLabel());

    }
}
