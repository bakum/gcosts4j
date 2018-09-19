package org.bakum.gcosts;

import org.bakum.gcosts.util.GraphUtils;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GcostsTest {

    private Gcosts<Node,Flow> g;
    private Node p1;
    private Node n901;
    private Node n231;

    @Before
    public void setUp() throws Exception {
        //Создаем узлы графа
        p1 = new Node("p1","Прямые затраты");
        Node n91 = new Node("91","Общепроизводственные затраты");
        n231 = new Node("231","Основное производство");
        Node n26 = new Node("26","Готовая продукция");
        n901 = new Node("901","Себестоимость реализованной продукции");

        //Создаем граф затрат
        g = new Gcosts<Node, Flow>(Flow.class);

        //Добавляем узлы в граф затрат
        g.addVertex(p1);
        g.addVertex(n91);
        g.addVertex(n231);
        g.addVertex(n26);
        g.addVertex(n901);

        //Устанавливаем потоки затрат (ребра графа) между узлами, направление от источника к получателю
        //(источник Кт, получатель Дт)
        Flow fp1_231 = (Flow) g.addEdge(p1,n231); //Первичные затраты на себестоимость
        fp1_231.setCost(new Costs(100.0,10.0));
        g.setEdgeWeight(fp1_231,fp1_231.getAmount());

        Flow f91_231 = (Flow) g.addEdge(n91,n231); //Первичные затраты на себестоимость
        f91_231.setCost(new Costs(200.0,1.0));
        g.setEdgeWeight(f91_231,f91_231.getAmount());

        Flow f231_26 = (Flow) g.addEdge(n231,n26); //Выпуск продукции
        f231_26.setCost(new Costs(0.0,10.0));
        g.setEdgeWeight(f231_26,f231_26.getAmount());

        Flow f26_901 = (Flow) g.addEdge(n26,n901); //Реализация продукции
        f26_901.setCost(new Costs(0.0,10.0));
        g.setEdgeWeight(f26_901,f26_901.getAmount());

    }

    @After
    public void tearDown() throws Exception {
        g = null;
        p1 = null;
        n901 = null;
        n231 = null;
    }

    @Test
    public void whenGetDijkstraShortestPath_thenGetNotNullPath() {
        DijkstraShortestPath dijkstraShortestPath
                = new DijkstraShortestPath(g);
        List<Node> shortestPath = dijkstraShortestPath
                .getPath(p1,n901).getVertexList();

        assertNotNull(shortestPath);
    }

    @Test
    public void incoming_edges() {

        Set<Node>ine = GraphUtils.getIncomingVertices(g,n231);
        //g.incomingEdgesOf(n231);

        assertEquals(2, ine.size());
    }

    @Test
    public void outcoming_edges() {

        Set<Node>out = GraphUtils.getOutgoingVertices(g,n231);
        //Set<Node> out = g.outgoingEdgesOf(n231);

        assertEquals(1, out.size());
    }
}