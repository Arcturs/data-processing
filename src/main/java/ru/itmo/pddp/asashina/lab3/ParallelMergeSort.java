package ru.itmo.pddp.asashina.lab3;

import java.util.concurrent.RecursiveAction;

import static ru.itmo.pddp.asashina.lab3.MergeSort.merge;

public class ParallelMergeSort extends RecursiveAction {

    private final int[] array;
    private final int[] helperArray;
    private final int left;
    private final int right;

    public ParallelMergeSort(int[] array, int left, int right) {
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
        var middle = (left + right) / 2;
        ParallelMergeSort left = new ParallelMergeSort(array, this.left, middle);
        ParallelMergeSort right = new ParallelMergeSort(array, middle + 1, this.right);
        invokeAll(left, right);
        merge(array, helperArray, this.left, middle, this.right);
    }

}
