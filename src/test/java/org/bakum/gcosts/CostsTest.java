package org.bakum.gcosts;

import org.bakum.gcosts.util.PojoTestUtils;
import org.junit.Test;


public class CostsTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(Costs.class);
    }

}