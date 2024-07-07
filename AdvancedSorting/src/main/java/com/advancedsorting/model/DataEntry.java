package com.advancedsorting.model;

import java.util.Arrays;

public class DataEntry {
    private int[] data;

    public DataEntry(int[] data) {
        this.data = data;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
