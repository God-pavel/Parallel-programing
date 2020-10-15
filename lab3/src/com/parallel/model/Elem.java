package com.parallel.model;

public class Elem {
    private final int index;
    private final int value;

    public Elem(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }
}
