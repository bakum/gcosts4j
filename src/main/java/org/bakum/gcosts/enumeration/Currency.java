package org.bakum.gcosts.enumeration;

public enum Currency {
    UAH("Гривна"),
    USD("Доллар США"),
    EURO("Евро");

    private String desc;

    Currency(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }
}
