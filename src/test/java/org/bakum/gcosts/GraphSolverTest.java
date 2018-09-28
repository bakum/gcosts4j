package org.bakum.gcosts;

import org.bakum.gcosts.enumeration.Currency;
import org.bakum.gcosts.util.GraphPatterns;
import org.bakum.gcosts.util.PojoTestUtils;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.ImportException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GraphSolverTest {

    private GraphSolver gs;

    @Before
    public void setUp() throws Exception {
        gs = GraphSolver.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        gs = null;
    }

    @Test
    public void setCurrencyRate() {
        gs.setCurrencyRate(Currency.EURO,31.0);

        Double rate = 31.0;
        assertEquals(rate, gs.getRate());
        assertEquals(Currency.EURO,gs.getCurrency());
    }

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(GraphSolver.class);
    }

    @Test
    public void saveToFile() {//TODO дописать тест сохранения в файл
        Gcosts<Node,Flow> graph = GraphPatterns.getInstance().getAccountingGraph(true);
        try {
            gs.saveToFile(graph,"test.graphml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExportException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadFromFile() {//TODO дописать тест загрузки из файла
        try {
            Gcosts<Node,Flow> graph = gs.loadFromFile("test.graphml", true);
            Gcosts<Node,Flow> g = GraphPatterns.getInstance().getAccountingGraph(true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImportException e) {
            e.printStackTrace();
        }
    }
}