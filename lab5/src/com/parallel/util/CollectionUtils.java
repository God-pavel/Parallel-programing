package com.parallel.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionUtils {
    public static List<Integer> generateList(final int size, final int min, final int max) {
        return IntStream.range(0, size)
                .mapToObj(el -> (int) (Math.random() * (max - min)) + min)
                .collect(Collectors.toList());
    }
    public static List<Integer> getAllWithoutIntersect(final List<Integer> list1, final List<Integer> list2) {
        final List<Integer> intersect = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());
        list1.addAll(list2);
        list1.removeAll(intersect);
        return list1;
    }
    public static List<Integer> sortList(final List<Integer> list) {
        Collections.sort(list);
        return list;
    }
}
