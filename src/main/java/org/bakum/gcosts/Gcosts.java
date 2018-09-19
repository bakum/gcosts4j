package org.bakum.gcosts;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.function.Supplier;

public class Gcosts extends DirectedWeightedPseudograph {

    public Gcosts(Class edgeClass) {
        super(edgeClass);
    }

    public Gcosts(Supplier vertexSupplier, Supplier edgeSupplier) {
        super(vertexSupplier, edgeSupplier);
    }

    public Gcosts(EdgeFactory ef) {
        super(ef);
    }
}
