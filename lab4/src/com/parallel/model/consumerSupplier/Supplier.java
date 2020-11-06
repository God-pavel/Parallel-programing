package com.parallel.model.consumerSupplier;

import com.parallel.model.Storage;

import static java.lang.Math.random;

public class Supplier extends Thread {

    private final int maxInterval;
    private final int minInterval;
    private final int maxDuration;
    private final int minDuration;
    private final int tasksToGenerate;
    private final Storage storage;

    public Supplier(final int maxInterval,
                    final int minInterval,
                    final int maxDuration,
                    final int minDuration,
                    final int tasksToGenerate,
                    final Storage storage) {
        this.maxDuration = maxDuration;
        this.minDuration = minDuration;
        this.maxInterval = maxInterval;
        this.minInterval = minInterval;
        this.tasksToGenerate = tasksToGenerate;
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < tasksToGenerate; i++) {
            final Task task = new Task(maxDuration, minDuration);
            storage.put(task);
            prepareTask();
        }
    }

    private void prepareTask() {
        try {
            sleep(minInterval + (int) (random() * (maxInterval - minInterval)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
