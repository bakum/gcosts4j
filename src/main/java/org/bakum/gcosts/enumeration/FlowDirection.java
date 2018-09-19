package org.bakum.gcosts.enumeration;

public enum FlowDirection {
    FLOW("Flow of the costs"),
    BEGIN_VALUE("Begin value of the costs"),
    END_VALUE("End value of the costs");

    private String desc;

    FlowDirection(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }
}
