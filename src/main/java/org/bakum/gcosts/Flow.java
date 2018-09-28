package org.bakum.gcosts;

import org.bakum.gcosts.enumeration.FlowDirection;
import org.bakum.gcosts.enumeration.FlowType;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Flow extends DefaultWeightedEdge {

    private Costs cost;
    private FlowDirection direction;
    private FlowType type;

    public Flow(){
        super();
        this.direction = FlowDirection.FLOW;
        this.type = FlowType.SECONDARY;
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

    public FlowType getType() {
        return type;
    }

    public void setType(FlowType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
