package com.parallel;

import com.parallel.executor.CompletableFutureExecutor;

public class Main {

    public static void main(String[] args) {
        final CompletableFutureExecutor executor = new CompletableFutureExecutor();
        executor.execute();
    }
}
