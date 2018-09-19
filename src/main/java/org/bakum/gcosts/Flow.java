package org.bakum.gcosts;

import org.bakum.gcosts.enumeration.FlowDirection;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Flow extends DefaultWeightedEdge {

    private Costs cost;
    private FlowDirection direction;

    public Flow(){
        super();
        this.direction = FlowDirection.FLOW;
    }

    public Flow(Costs v1){
        this();
        setCost(v1);
    }

    public Costs getCost() {
        return cost;
    }

    public void setCost(Costs costs) {
        this.cost = costs;
    }

    public Double getAmount() {
        if (this.cost == null) return 0.0;
        Double price = this.cost.getPrice();
        Double quantity = this.cost.getQuantity();
        return price * quantity;
    }

    public FlowDirection getDirection() {
        return direction;
    }

    public void setDirection(FlowDirection direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
