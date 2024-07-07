package com.advancedsorting.service;

import com.advancedsorting.dao.DataEntryDAO;
import com.advancedsorting.model.DataEntry;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DataEntryService {
    @Inject
    private DataEntryDAO dataEntryDAO;

    public void addDataEntry(int[] data) {
        dataEntryDAO.addDataEntry(new DataEntry(data));
    }

    public List<DataEntry> getAllDataEntries() {
        return dataEntryDAO.getAllDataEntries();
    }

    public void updateDataEntry(int index, int[] data) {
        dataEntryDAO.updateDataEntry(index, new DataEntry(data));
    }

    public void deleteDataEntry(int index) {
        dataEntryDAO.deleteDataEntry(index);
    }
}
