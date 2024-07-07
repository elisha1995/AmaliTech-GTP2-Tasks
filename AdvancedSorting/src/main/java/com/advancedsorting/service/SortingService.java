package com.advancedsorting.service;

import com.advancedsorting.util.SortingAlgorithms;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SortingService {
    public int[] sort(int[] data, String algorithm) {
        int[] sortedData = data.clone();
        switch (algorithm) {
            case "heapSort":
                SortingAlgorithms.heapSort(sortedData);
                break;
            case "quickSort":
                SortingAlgorithms.quickSort(sortedData, 0, sortedData.length - 1);
                break;
            case "mergeSort":
                SortingAlgorithms.mergeSort(sortedData, 0, sortedData.length - 1);
                break;
            case "radixSort":
                SortingAlgorithms.radixSort(sortedData);
                break;
            case "bucketSort":
                SortingAlgorithms.bucketSort(sortedData);
                break;
        }
        return sortedData;
    }
}
