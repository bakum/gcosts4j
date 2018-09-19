package org.bakum.gcosts;

import org.bakum.gcosts.interfaces.ICosts;

public class Costs implements ICosts {
    private Double price;      //Тариф
    private Double quantity;  //Количество

    public Costs() {
        this.price = 0.0;
        this.quantity = 0.0;
    }

    public Costs(Double price, Double quantity) {
        this.price = price;
        this.quantity = quantity;
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
    public void setPrice(Double c) {
        this.price = c;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }

}
