package org.bakum.gcosts;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Flow extends DefaultWeightedEdge {

    private Costs cost;

    public Flow(){
        super();
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

    public boolean isLoop() {
        if (getSource().equals(getTarget())) return true;

        return false;
    }

    public boolean isEndDebt() {
        if (isLoop() && (getWeight() < 0.0)) return true;

        return false;
    }

    public boolean isBeginDebt() {
        if (isLoop() && (getWeight() > 0.0)) return true;

        return false;
    }

    public Double getAmount() {
        Double price = this.cost.getPrice();
        Double quantity = this.cost.getQuantity();
        return price * quantity;
    }

}
