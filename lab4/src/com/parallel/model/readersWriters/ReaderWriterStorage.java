package com.parallel.model.readersWriters;

import com.parallel.model.Storage;
import com.parallel.model.consumerSupplier.Task;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReaderWriterStorage implements Storage {
    private final ArrayDeque<Task> storage = new ArrayDeque<>();
    private final ReentrantLock writerLock = new ReentrantLock();
    private final ReentrantLock readerLock = new ReentrantLock();
    private final Condition storageCondition = readerLock.newCondition();
    private int readerCounter = 0;

    @Override
    public final void put(final Task task) {
        writerLock.lock();
        while (readerCounter != 0) {
            waitForReaders();
        }
        readerLock.lock();
        try {
            storage.add(task);
            System.out.println("putting into queue task with " + task.getDuration() + "mls duration");
            storageCondition.signalAll();
        } finally {
            readerLock.unlock();
            writerLock.unlock();
        }
    }

    @Override
    public final Task getTask() {
        readerLock.lock();
        readerCounter++;
        try {
            while (storage.size() < 1) {
                storageCondition.await();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Task task = storage.getLast();
        readerCounter--;
        readerLock.unlock();
        System.out.println("getting task with " + task.getDuration() + "mls duration");
        return task;
    }

    private void waitForReaders() {
        try {
            System.out.println("Waiting for readers: " + readerCounter);
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
