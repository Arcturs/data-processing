package ru.itmo.pddp.asashina.lab3;

public class MergeSort {

    public static void mergesort(int[] array) {
        var helperArray = new int[array.length];
        mergesort(array, helperArray, 0, array.length - 1);
    }

    public static void mergesort(int[] array, int[] helperArray, int left, int right) {
        if (left >= right) {
            return;
        }
        var middle = (left + right) / 2;
        mergesort(array, helperArray, left, middle);
        mergesort(array, helperArray, middle + 1, right);
        merge(array, helperArray, left, middle, right);
    }

    public static void merge(int[] array, int[] helperArray, int left, int middle, int right) {
        for (var i = left; i <= right; i++) {
            helperArray[i] = array[i];
        }

        var helperLeft = left;
        var helperRight = middle + 1;
        var current = left;
        while (helperLeft <= middle && helperRight <= right) {
            if (helperArray[helperLeft] <= helperArray[helperRight]) {
                array[current] = helperArray[helperLeft++];
            } else {
                array[current] = helperArray[helperRight++];
            }
            current++;
        }

        while (helperLeft <= middle) {
            array[current++] = helperArray[helperLeft++];
        }
    }

}