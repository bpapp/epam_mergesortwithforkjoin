package com.epam.bpapp.multithreading;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Predicate;

class ForkJoinTest {

    private static final int MAX_SIZE = 100000;

    @Test
    @DisplayName("first case")
    void testMergeSort() throws IOException {
        // When
        Integer[] sortedArray = new ForkJoinDemo().startMergeAndSort("src/main/resources/first_example.txt");

        // Then
        then(sortedArray).isNotNull().isNotEmpty();
        then(getExpectedArrayForFirstCase()).isEqualTo(sortedArray);
    }

    @Test
    @DisplayName("second case")
    void testMergeSortWithBigInput() throws IOException {
        // When
        Integer[] sortedArray = new ForkJoinDemo().startMergeAndSort(generateLotOfRandomInteger());

        // Then
        then(sortedArray).isNotNull().isNotEmpty();
        Predicate<Integer> lesserthan = i -> (i <= i + 1);
        then(sortedArray).hasSizeLessThan(MAX_SIZE + 1);
        then(sortedArray).isSorted();
        then(sortedArray).allMatch(lesserthan);
    }

    Integer[] getExpectedArrayForFirstCase() {
        return new Integer[]{
                1, 2, 2, 3, 3, 3, 5, 6, 6, 7, 7, 8, 9, 10, 10, 13, 14, 14, 20, 21, 24, 54, 64, 79, 79, 84, 87, 88,
                89, 94, 100
        };
    }

    private String generateLotOfRandomInteger() throws IOException {
        int arr[] = new int[MAX_SIZE];
        Random rand = new Random();
        for (int j = 0; j < MAX_SIZE; j++) {
            arr[j] = rand.nextInt(MAX_SIZE);
        }
        final String filePath = "src/main/resources/big_example.txt";
        Files.write(Paths.get(filePath),
                (Iterable<String>) Arrays.stream(arr).mapToObj(String::valueOf)::iterator);
        return filePath;
    }
}