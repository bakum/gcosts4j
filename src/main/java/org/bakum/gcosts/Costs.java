package org.bakum.gcosts;

import org.bakum.gcosts.interfaces.ICosts;

public class Costs implements ICosts {
    private Double cost;      //Стоимость
    private Double quantity;  //Количество

    public Costs() {
        this.cost = 0.0;
        this.quantity = 0.0;
    }

    public Costs(Double c, Double q) {
        this.cost = c;
        this.quantity = q;
    }

    @Override
    public void setQuantity(Double q) {
        this.quantity = q;
    }

    @Override
    public Double getQuantity() {
        return this.quantity;
    }

    @Override
    public void setCost(Double c) {
        this.cost = c;
    }

    @Override
    public Double getCost() {
        return this.cost;
    }
}
