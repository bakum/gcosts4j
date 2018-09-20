package org.bakum.gcosts;

import org.bakum.gcosts.enumeration.Currency;

public class GraphSolver {
    private static GraphSolver instance;
    private Currency currency;            //Валюта расчета
    private Double rate;                  //Курс

    public static GraphSolver getInstance() {
        if (instance == null) {
            instance = new GraphSolver();
        }
        return instance;
    }

    private GraphSolver() {
        this.currency = Currency.UAH;
        this.rate = 1.0;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        if (this.currency != currency) {
            this.rate = 0.0;
        }
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setCurrencyRate(Currency currency, Double rate){
        this.currency = currency;
        this.rate = rate;
    }
}
