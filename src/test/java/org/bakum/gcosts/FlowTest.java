package org.bakum.gcosts;

import org.bakum.gcosts.util.PojoTestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlowTest {

    @Test
    public void getAmountTest() {
        Flow fl = new Flow();
        fl.setCost(new Costs(10.0,22.4));
        Double result = 224.0;
        assertEquals(result,fl.getAmount());
    }

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(Flow.class);
    }
}