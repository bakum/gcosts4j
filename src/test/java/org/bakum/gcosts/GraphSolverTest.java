package org.bakum.gcosts;

import org.bakum.gcosts.enumeration.Currency;
import org.bakum.gcosts.util.PojoTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
}