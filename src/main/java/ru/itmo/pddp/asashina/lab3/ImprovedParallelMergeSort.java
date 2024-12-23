package ru.itmo.pddp.asashina.lab3;

import java.util.concurrent.RecursiveAction;

import static ru.itmo.pddp.asashina.lab3.MergeSort.merge;
import static ru.itmo.pddp.asashina.lab3.MergeSort.mergesort;

public class ImprovedParallelMergeSort extends RecursiveAction {

    private static final int ELEMENTS_SIZE_LIMIT = 1 << 8;

    private final int[] array;
    private final int[] helperArray;
    private final int left;
    private final int right;

    public ImprovedParallelMergeSort(int[] array, int left, int right) {
        this.array = array;
        helperArray = new int[array.length];
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left >= right) {
            return;
        }

        if (right - left <= ELEMENTS_SIZE_LIMIT) {
            mergesort(array, helperArray, left, right);
            return;
        }

        var middle = (left + right) / 2;
        var left = new ImprovedParallelMergeSort(array, this.left, middle);
        var right = new ImprovedParallelMergeSort(array, middle + 1, this.right);
        invokeAll(left, right);
        merge(array, helperArray, this.left, middle, this.right);
    }

}
