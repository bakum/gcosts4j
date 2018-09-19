package org.bakum.gcosts;

import org.junit.Test;

import static org.junit.Assert.assertThat;

public class NodeTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(Node.class);
    }

    @Test
    public void toStringTest() {
        Node n = new Node("v1","Desc");
        String guid = n.getGuid();
        //assertThat(n.toString(),);
    }
}