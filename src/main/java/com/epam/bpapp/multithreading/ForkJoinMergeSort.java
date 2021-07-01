package com.epam.bpapp.multithreading;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ForkJoinMergeSort {

    void merge(int[] array, int l, int m, int r) {
        int i;
        int j;
        int[] temporaryArray = new int[array.length];
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

    void mergeSort(int[] array, int lower, int upper) {
        if (upper <= lower) {
            return;
        }
        int middle = (upper + lower) / 2;
        mergeSort(array, lower, middle);
        mergeSort(array, middle + 1, upper);
        merge(array, lower, middle, upper);
    }

    static void printArray(int[] arr) {
        final String result = Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(" "));
        System.out.println(result);
    }
}

