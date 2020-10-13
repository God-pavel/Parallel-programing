package com.parallel.model;

import static java.lang.Math.random;

public class CPUProcess extends Thread{

    private final int maxInterval;
    private final int minInterval;
    private final int maxDuration;
    private final int minDuration;
    private final int tasksToGenerate;
    private final CPUQueue cpuQueue;

    public CPUProcess(final int maxInterval,
                      final int minInterval,
                      final int maxDuration,
                      final int minDuration,
                      final int tasksToGenerate,
                      final CPUQueue cpuQueue){
        this.maxDuration = maxDuration;
        this.minDuration = minDuration;
        this.maxInterval = maxInterval;
        this.minInterval = minInterval;
        this.tasksToGenerate = tasksToGenerate;
        this.cpuQueue = cpuQueue;
    }

    @Override
    public void run() {
        for(int i = 0; i < tasksToGenerate; i++){
            final CPUTask task = new CPUTask(maxDuration, minDuration);
            cpuQueue.put(task);
            prepareTask();
        }
    }

    private void prepareTask(){
        try {
            sleep(minInterval + (int) (random() * (maxInterval - minInterval)));
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public int getTasksToGenerate() {
        return tasksToGenerate;
    }
}
