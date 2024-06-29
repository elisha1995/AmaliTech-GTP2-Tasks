package com.textprocessor.model;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<String> dataList = new ArrayList<>();

    public void addData(String data) {
        dataList.add(data);
    }

    public void updateData(int index, String newData) {
        if (index >= 0 && index < dataList.size()) {
            dataList.set(index, newData);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void deleteData(int index) {
        if (index >= 0 && index < dataList.size()) {
            dataList.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public List<String> getAllData() {
        return new ArrayList<>(dataList); // Return a copy to prevent external modification
    }

}
