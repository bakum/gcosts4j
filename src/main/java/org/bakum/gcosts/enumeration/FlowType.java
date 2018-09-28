package org.bakum.gcosts.enumeration;

public enum FlowType {
    PRIMARY("Primary"),
    SECONDARY("Secondary");

    private String desc;

    FlowType(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }
}
