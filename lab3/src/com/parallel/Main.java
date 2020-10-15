package com.parallel;

import com.parallel.model.Elem;

import java.util.Map;

import static com.parallel.util.ParallelUtils.*;

public class Main {
    public static void main(String[] args) {
        final int[] array = generateArray(1000);
        final int numberOfElements = calcNumberOfElements(array);
        final Map<String, Elem> minMax = calcMinMaxValue(array);
        final int sumOfElements = calcSumOfElements(array);

        System.out.println("Number: " + numberOfElements);
        System.out.println("Min index: " + minMax.get("Min").getIndex());
        System.out.println("Min value: " + minMax.get("Min").getValue());
        System.out.println("Max index: " + minMax.get("Max").getIndex());
        System.out.println("Max value: " + minMax.get("Max").getValue());
        System.out.println("Sum: " + sumOfElements);
    }
}