package com.parallel.model.consumerSupplier;

import com.parallel.model.Storage;

public class Consumer extends Thread {
    private final Storage storage;
    private int completedTasks;

    public Consumer(final Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            final Task task = storage.getTask();
            runTask(task);
            completedTasks++;
        }
    }

    private void runTask(final Task task) {

        System.out.println(getName() + " - " + "run task.");
        try {
            Thread.sleep(task.getDuration());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public final int getCompletedTasks() {
        return completedTasks;
    }
}
