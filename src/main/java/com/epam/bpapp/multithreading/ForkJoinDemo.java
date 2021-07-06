package com.epam.bpapp.multithreading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ForkJoinMergeSort extends RecursiveTask<Void> {

    private Integer[] intArray;

    private int lowerIndex;
    private int upperIndex;

    public ForkJoinMergeSort(Integer[] intArray, Integer lowerIndex, Integer upperIndex) {
        this.intArray = intArray;
        this.lowerIndex = lowerIndex;
        this.upperIndex = upperIndex;
    }

    public Integer[] getIntArray() {
        return intArray;
    }

    public void setIntArray(Integer[] intArray) {
        this.intArray = intArray;
    }

    void merge(Integer[] array, int l, int m, int r) {
        int i;
        int j;
        Integer[] temporaryArray = new Integer[array.length];
        for (i = m + 1; i > l; i--) {
            temporaryArray[i - 1] = array[i - 1];
        }
        for (j = m; j < r; j++) {
            temporaryArray[r + m - j] = array[j + 1];
        }
        for (int k = l; k <= r; k++) {
            array[k] = temporaryArray[j] < temporaryArray[i] ? temporaryArray[j--] : temporaryArray[i++];
        }
    }

    @Override
    protected Void compute() {
        if (lowerIndex < upperIndex) {
            int mid = (lowerIndex + upperIndex) / 2;

            ForkJoinMergeSort left = new ForkJoinMergeSort(intArray, lowerIndex, mid);
            ForkJoinMergeSort right = new ForkJoinMergeSort(intArray, mid + 1, upperIndex);

            invokeAll(left, right);
            merge(intArray, lowerIndex, mid, upperIndex);
        }
        return null;
    }
}

public class ForkJoinDemo {

    public Integer[] startMergeAndSort(String fileName) {
        Integer[] readInArray = readIn(fileName);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinTask<?> task = new ForkJoinMergeSort(readInArray, 0, readInArray.length - 1);
        pool.invoke(task);
        pool.shutdown();
        printArray(readInArray);
        return readInArray;
    }

    private Integer[] readIn(String fileName) {
        Integer[] arr = new Integer[0];
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            arr = stream.
                    mapToInt(Integer::valueOf).boxed().toArray(Integer[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    void printArray(Integer[] arr) {
        final String result = Arrays.stream(arr).map(String::valueOf).collect(Collectors.joining(", "));
        System.out.println(result);
    }

}

