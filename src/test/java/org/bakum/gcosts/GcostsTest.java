package org.bakum.gcosts;

import org.bakum.gcosts.util.GraphPatterns;
import org.bakum.gcosts.util.GraphUtils;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class GcostsTest {

    private Gcosts<Node,Flow> g;
    private Node p1;
    private Node n901;
    private Node n231;
    private Node n91;
    private Node n26;

    @Before
    public void setUp() throws Exception {
        g = GraphPatterns.getInstance().getAccountingGraph(true);
        //g = GraphSolver.getInstance().loadFromFile("test.graphml");

        p1 = GraphUtils.getVerticeByName(g,"p1");
        n91 = GraphUtils.getVerticeByName(g,"91");
        n231 = GraphUtils.getVerticeByName(g,"231");
        n26 = GraphUtils.getVerticeByName(g,"26");
        n901 = GraphUtils.getVerticeByName(g,"901");

    }

    @After
    public void tearDown() throws Exception {
        g = null;
        p1 = null;
        n901 = null;
        n231 = null;
        n91 = null;
        n26 = null;
    }

    @Test
    public void whenGetDijkstraShortestPath_thenGetNotNullPath() {
        DijkstraShortestPath dijkstraShortestPath
                = new DijkstraShortestPath(g);
        List<Node> shortestPath = dijkstraShortestPath
                .getPath(n91,n901).getVertexList();

        assertNotNull(shortestPath);
    }

    @Test
    public void whenCheckCycles_thenDetectCycles() {
        CycleDetector<Node, Flow> cycleDetector
                = new CycleDetector<Node, Flow>(g);

        assertTrue(cycleDetector.detectCycles());
        Set<Node> cycleVertices = cycleDetector.findCycles();

        assertTrue(cycleVertices.size() > 0);
    }

    @Test
    public void incoming_edges() {

        Set<Node> ine = GraphUtils.getIncomingVertices(g,n231);
        Set<Flow> fl =  g.incomingEdgesOf(n231);

        assertEquals(3, ine.size());
        assertEquals(4, fl.size());
    }

    @Test
    public void outcoming_edges() {

        Set<Node> out = GraphUtils.getOutgoingVertices(g,n231);
        Set<Flow> fl =  g.outgoingEdgesOf(n231);
        assertEquals(2, out.size());
        assertEquals(3, fl.size());
    }

    @Test
    public void Incoming_outcoming_edges() {

        Set<Node>out = GraphUtils.getOutgoingVertices(g,n231);
        Set<Node>ine = GraphUtils.getIncomingVertices(g,n231);

        assertEquals(2, out.size());
        assertEquals(3, ine.size());
    }
}