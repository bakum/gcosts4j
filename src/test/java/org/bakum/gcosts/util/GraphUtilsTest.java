package org.bakum.gcosts.util;

import org.bakum.gcosts.Flow;
import org.bakum.gcosts.Gcosts;
import org.bakum.gcosts.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GraphUtilsTest {

    private Gcosts<Node, Flow> g;

    @Before
    public void setUp() throws Exception {
        g = GraphPatterns.getInstance().getAccountingGraph(true);
    }

    @After
    public void tearDown() throws Exception {
        g = null;
    }

    @Test
    public void getLeafVertices() {
        Set<Node> nd = GraphUtils.getLeafVertices(g);
        assertEquals(1, nd.size());
    }

    @Test
    public void getRootVertices() {
        Set<Node> nd = GraphUtils.getRootVertices(g);
        assertEquals(2, nd.size());
    }

    @Test
    public void getMiddleVertices() {
        Set<Node> nd = GraphUtils.getMiddleVertices(g);
        assertEquals(2, nd.size());
    }
}