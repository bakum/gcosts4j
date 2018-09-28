package org.bakum.gcosts;

import java.util.UUID;

public class Node {
    private String guid;
    private String name;
    private String description;

    public Node() {

        this.guid = UUID.randomUUID().toString();
    }

    public Node(String id) {
        this.guid = id;
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

//        return this.guid + " - " + this.name + ": " + this.description;
        return this.name + ": " + this.description;
    }

    @Override
    public int hashCode()
    {
        return (name == null) ? 0 : name.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (name == null) {
            return other.name == null;
        } else {
            return name.equals(other.name);
        }
    }
}
