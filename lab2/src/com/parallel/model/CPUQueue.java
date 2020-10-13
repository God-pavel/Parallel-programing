package com.parallel.model;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class CPUQueue {
    private final Queue<CPUTask> queue = new ArrayDeque<>();
    private int maxSize = 0;
    private int allElements = 0;

    public final synchronized void put(final CPUTask task) {

        System.out.println("putting into queue task with " + task.getDuration() + "mls duration");

        queue.add(task);
        updateSize();
        notifyAll();
    }

    public final synchronized Optional<CPUTask> getTask() {

        System.out.println("getting task");

        return Optional.ofNullable(queue.poll());
    }

    private void updateSize() {
        allElements++;
        if (queue.size() > maxSize) {
            maxSize = queue.size();
        }
    }

    public final int getMaxSize() {
        return maxSize;
    }

    public final int getAllElements(){
        return allElements;
    }
}
