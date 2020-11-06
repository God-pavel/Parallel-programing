package com.parallel.model.consumerSupplier;

import com.parallel.model.Storage;

import java.util.ArrayDeque;
import java.util.Queue;

public class ConsumerSupplierStorage implements Storage {
    private final Queue<Task> storage = new ArrayDeque<>();
    private final int maxSize;

    public ConsumerSupplierStorage(int maxSize){
        this.maxSize = maxSize;
    }

    @Override
    public final synchronized void put(final Task task) {
        if (storage.size() == maxSize) {
            System.out.println("Storage is full. Waiting...");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("putting into queue task with " + task.getDuration() + "mls duration");
        storage.add(task);
        notifyAll();
    }

    @Override
    public final synchronized Task getTask() {
        if (storage.size() == 0) {
            System.out.println("Waiting for task...");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("getting task");
        Task taskToReturn = storage.poll();
        notifyAll();
        return taskToReturn;
    }
}
