package com.epam.bpapp.multithreading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ForkJoinMergeSort extends RecursiveTask {

    private Integer[] intArray;

    private int lower, upper;

    public ForkJoinMergeSort(Integer[] intArray, Integer lower, Integer upper) {
        this.intArray = intArray;
        this.lower = lower;
        this.upper = upper;
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
    protected Object compute() {
        if (lower < upper) {
            int mid = (lower + upper) / 2;

            ForkJoinMergeSort left = new ForkJoinMergeSort(intArray, lower, mid);
            ForkJoinMergeSort right = new ForkJoinMergeSort(intArray, mid + 1, upper);

            invokeAll(left, right);
            merge(intArray, lower, mid, upper);
        }
        return Optional.empty();
    }
}

public class ForkJoinDemo {

    public static void main(String[] args) {
        Integer[] readInArray = readIn(args[0]);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinTask<?> task = new ForkJoinMergeSort(readInArray, 0, readInArray.length - 1);
        pool.invoke(task);
        pool.shutdown();
        printArray(readInArray);
    }

    private static Integer[] readIn(String fileName) {
        Integer[] arr = null;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            arr = stream.
                    mapToInt(value -> Integer.valueOf(value)).boxed().toArray(Integer[]::new);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    static void printArray(Integer[] arr) {
        final String result = Arrays.stream(arr).map(x -> String.valueOf(x)).collect(Collectors.joining(" "));
        System.out.println(result);
    }

}

