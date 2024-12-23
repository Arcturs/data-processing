package ru.itmo.pddp.asashina.lab3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static ru.itmo.pddp.asashina.lab3.MergeSort.mergesort;

public class MergeSortTest {

    private static final Random RANDOM = new Random();

    private final ForkJoinPool forkJoinPool = new ForkJoinPool(8);

    @ParameterizedTest
    @CsvSource({"1", "4", "16", "32", "256", "1024", "8192", "16384", "32768", "60000", "100000", "450000", "1000000"})
    void sortTest(int size) {
        var smallArray = generateRandomArray(size);
        System.out.println("-- Размер массива: " + size + " --");

        var smallArraySequential = new int[smallArray.length];
        System.arraycopy(smallArray, 0, smallArraySequential, 0, smallArray.length);
        var now = System.currentTimeMillis();
        mergesort(smallArraySequential);
        var time = System.currentTimeMillis() - now;
        System.out.println(" - Сортировка слиянием в 1 потоке - ");
        System.out.println("   Время сортировки: " + time + " мс");

        var smallArrayParallel = new int[smallArray.length];
        System.arraycopy(smallArray, 0, smallArrayParallel, 0, smallArray.length);
        now = System.currentTimeMillis();
        forkJoinPool.invoke(new ParallelMergeSort(smallArrayParallel, 0, smallArray.length - 1));
        time = System.currentTimeMillis() - now;
        System.out.println(" - Сортировка слиянием параллельно - ");
        System.out.println("   Время сортировки: " + time + " мс");

        var smallArrayImprovedParallel = new int[smallArray.length];
        System.arraycopy(smallArray, 0, smallArrayImprovedParallel, 0, smallArray.length);
        now = System.currentTimeMillis();
        forkJoinPool.invoke(
                new ImprovedParallelMergeSort(smallArrayImprovedParallel, 0, smallArray.length - 1));
        time = System.currentTimeMillis() - now;
        System.out.println(" - Сортировка слиянием параллельно (улучшенная) - ");
        System.out.println("   Время сортировки: " + time + " мс");
    }

    private static int[] generateRandomArray(int size) {
        var array = new int[size];
        for (var i = 0; i < size; i++) {
            array[i] = RANDOM.nextInt(1_000_000);
        }
        return array;
    }

}
