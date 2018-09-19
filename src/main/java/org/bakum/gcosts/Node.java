package org.bakum.gcosts;

import java.util.UUID;

public class Node {
    private String guid;
    private String name;
    private String description;

    public Node() {
        this.guid = UUID.randomUUID().toString();
    }

    public Node(String n, String d) {
        this();
        this.name = n;
        this.description = d;
    }

    public String getGuid() {
        return guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {

        return this.guid + " - " + this.name + ": " + this.description;
//        return this.name + ": " + this.description;
    }
}
