package com.parallel.executor;

import com.parallel.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.parallel.util.CollectionUtils.generateList;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class CompletableFutureExecutor {
    public final void execute() {
        final List<Integer> list1 = generateList(10, 0, 10);
        final List<Integer> list2 = generateList(10, 5, 15);

        System.out.println("list1: " + list1);
        System.out.println("list2: " + list2);

        final CompletableFuture<Double> averageOfList1Future = supplyAsync(() ->
                list1.stream()
                        .mapToInt(e -> e)
                        .average().getAsDouble());

        final CompletableFuture<Double> averageOfList2Future = supplyAsync(() ->
                list2.stream()
                        .mapToInt(e -> e)
                        .average().getAsDouble());

        System.out.println("Average of list1: " + getResultFromFuture(averageOfList1Future));
        System.out.println("Average of list2: " + getResultFromFuture(averageOfList2Future));

        final CompletableFuture<List<Integer>> list1Future = averageOfList1Future
                .thenApplyAsync(average -> list1.stream()
                        .filter(el -> el > average)
                        .collect(Collectors.toList()))
                .thenApplyAsync(CollectionUtils::sortList);

        final CompletableFuture<List<Integer>> list2Future = averageOfList2Future
                .thenApplyAsync(average -> list2.stream()
                        .filter(el -> el < average)
                        .collect(Collectors.toList()))
                .thenApplyAsync(CollectionUtils::sortList);

        System.out.println("Modified list1: " + getResultFromFuture(list1Future));
        System.out.println("Modified list2: " + getResultFromFuture(list2Future));

        final CompletableFuture<List<Integer>> resultList = list1Future
                .thenCombineAsync(list2Future, CollectionUtils::getAllWithoutIntersect)
                .thenApplyAsync(CollectionUtils::sortList);


        System.out.println("Result list: " + getResultFromFuture(resultList));
    }

    private <T> T getResultFromFuture(final CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}




