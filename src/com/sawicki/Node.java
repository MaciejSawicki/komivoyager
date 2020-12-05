package com.sawicki;

import java.util.List;

public class Node {
    private int id;
    private String label;


    Node(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
