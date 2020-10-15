package com.parallel.util;

import com.parallel.model.Elem;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelUtils {
    public static int[] generateArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int calcNumberOfElements(final int[] array) {
        final AtomicInteger atomicNum = new AtomicInteger();
        IntStream.of(array).parallel().forEach(x -> {
            int oldValue;
            int newValue;
            do {
                oldValue = atomicNum.get();
                newValue = oldValue + 1;
            } while (!atomicNum.compareAndSet(oldValue, newValue));
        });
        return atomicNum.get();
    }

    public static Map<String, Elem> calcMinMaxValue(final int[] array) {
        final AtomicInteger atomicMinIndex = new AtomicInteger();
        final AtomicInteger atomicMaxIndex = new AtomicInteger();
        IntStream.range(0, array.length)
                .parallel()
                .forEach(i -> {
                    int oldMinValue;
                    int newMinValue;
                    int oldMaxValue;
                    int newMaxValue;
                    do {
                        oldMinValue = array[atomicMinIndex.get()];
                        newMinValue = Math.min(oldMinValue, array[i]);
                    } while (!atomicMinIndex.compareAndSet(oldMinValue, newMinValue));
                    do {
                        oldMaxValue = array[atomicMaxIndex.get()];
                        newMaxValue = Math.max(oldMaxValue, array[i]);
                    } while (!atomicMaxIndex.compareAndSet(oldMaxValue, newMaxValue));
                });
        return Map.of("Min", new Elem(atomicMinIndex.get(), array[atomicMinIndex.get()]),
                "Max", new Elem(atomicMaxIndex.get(), array[atomicMaxIndex.get()]));
    }

    public static int calcSumOfElements(final int[] array) {
        final AtomicInteger atomicSum = new AtomicInteger();
        IntStream.of(array).parallel().forEach(x -> {
            int oldValue;
            int newValue;
            do {
                oldValue = atomicSum.get();
                newValue = oldValue ^ x;
            } while (!atomicSum.compareAndSet(oldValue, newValue));
        });
        return atomicSum.get();
    }
}
