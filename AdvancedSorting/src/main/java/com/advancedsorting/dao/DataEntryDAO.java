package com.advancedsorting.dao;

import com.advancedsorting.model.DataEntry;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DataEntryDAO {
    private List<DataEntry> dataEntries = new ArrayList<>();

    public void addDataEntry(DataEntry dataEntry) {
        dataEntries.add(dataEntry);
    }

    public List<DataEntry> getAllDataEntries() {
        return dataEntries;
    }

    public void updateDataEntry(int index, DataEntry dataEntry) {
        dataEntries.set(index, dataEntry);
    }

    public void deleteDataEntry(int index) {
        dataEntries.remove(index);
    }
}
