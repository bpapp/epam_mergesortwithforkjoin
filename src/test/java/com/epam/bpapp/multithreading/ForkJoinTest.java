package com.epam.bpapp.multithreading;

import org.junit.jupiter.api.Test;

class ForkJoinTest {

    @Test
    void testMergeSort() {

        ForkJoinDemo.main(new String[]{"src/main/resources/first_example.txt"});
        //Integer[] original = getInputArray();
        // ForkJoinMergeSort forkJoinMergeSort = new ForkJoinMergeSort(original);
        //Integer[] sorting = getInputArray();
        //   ForkJoinMergeSort.printArray(original);
//        forkJoinMergeSort.mergeSort(sorting, 0, sorting.length - 1);
//        ForkJoinMergeSort.printArray(sorting);
//        final int[] expectedArray = new int[]{2, 3, 5, 6, 6, 7, 7, 9, 10, 10, 23, 34, 35, 45, 46, 54, 87, 89, 98, 98};
//        Assertions.assertArrayEquals(expectedArray, sorting);
    }

    Integer[] getInputArray() {
        return new Integer[]{
                3, 6, 10, 98, 45, 2, 5, 7, 98, 35, 34, 10, 54, 6, 7, 9, 46, 87, 89, 23
        };
    }

}