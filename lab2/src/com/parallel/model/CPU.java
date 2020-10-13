package com.parallel.model;

import java.util.Optional;

public class CPU extends Thread {
    private final CPUQueue queue;
    private final CPUQueue priorityQueue;
    private int priorityQueueTasksNumber = 0;
    private int totalTasksNumber = 0;

    public CPU(final CPUQueue queue,
               final CPUQueue priorityQueue) {
        this.queue = queue;
        this.priorityQueue = priorityQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            final CPUTask task = getTask();
            runTask(task);
        }
    }

    private CPUTask getTask() {
        final Optional<CPUTask> priorityTask = priorityQueue.getTask();
        if (priorityTask.isEmpty()) {
            return queue.getTask()
                    .orElseGet(this::waitForTask);
        } else {
            priorityQueueTasksNumber++;
            return priorityTask.get();
        }
    }

    private CPUTask waitForTask() {
        synchronized (queue) {
            return waitQueueTask();
        }
    }

    private CPUTask waitQueueTask() {
        System.out.println(getName() + " - " + "waiting for task.");
        try {
            queue.wait();
            return queue.getTask().get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    private void runTask(final CPUTask task) {
        if(task==null){
            return;
        }
        System.out.println(getName() + " - " + "run task.");
        try {
            Thread.sleep(task.getDuration());
            totalTasksNumber++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getTotalTasksNumber() {
        return totalTasksNumber;
    }

    public int getPriorityQueueTasksNumber() {
        return priorityQueueTasksNumber;
    }
}
