package org.bakum.gcosts;

import org.bakum.gcosts.util.PojoTestUtils;
import org.junit.Test;

public class NodeTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(Node.class);
    }

    @Test
    public void toStringTest() {
        Node n = new Node("v1","Desc");
        String guid = n.getGuid();//TODO Дописать тест toString() для класса Node
        //assertThat(n.toString(),);
    }
}