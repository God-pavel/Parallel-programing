package com.parallel;

import com.parallel.model.CPU;
import com.parallel.model.CPUProcess;
import com.parallel.model.CPUQueue;

public class Main {

    public static void main(String[] args) {
        final CPUQueue cpuQueue1 = new CPUQueue();
        final CPUQueue cpuQueue2 = new CPUQueue();
        final CPUQueue cpuQueue3 = new CPUQueue();
        final CPUProcess cpuProcess1 = new CPUProcess(2000, 1000, 3000, 1000, 10, cpuQueue1);
        final CPUProcess cpuProcess2 = new CPUProcess(2000, 1000, 3000, 1000, 10, cpuQueue2);
        final CPUProcess cpuProcess3 = new CPUProcess(2000, 1000, 3000, 1000, 5, cpuQueue3);
        final CPU cpu1 = new CPU(cpuQueue1, cpuQueue3);
        final CPU cpu2 = new CPU(cpuQueue2, cpuQueue3);

        cpuProcess1.start();
        cpuProcess2.start();
        cpuProcess3.start();

        cpu1.start();
        cpu2.start();

        while (true) {
            int completedTasks = cpu1.getTotalTasksNumber() + cpu2.getTotalTasksNumber();
            int tasksToComplete = cpuProcess1.getTasksToGenerate() + cpuProcess2.getTasksToGenerate() + cpuProcess3.getTasksToGenerate();
            System.out.println(completedTasks);
            System.out.println(tasksToComplete);
            if (completedTasks == tasksToComplete) {
                cpu1.interrupt();
                cpu2.interrupt();
                break;
            }
        }
        System.out.println("CPU1 have run: " + cpu1.getTotalTasksNumber() + " tasks.");
        System.out.println("CPU1 have run: " + (double) cpu1.getPriorityQueueTasksNumber() / (double) cpuQueue3.getAllElements() * 100L  + "% from queue3 tasks");

        System.out.println("CPU2 have run: " + cpu2.getTotalTasksNumber() + " tasks.");
        System.out.println("CPU2 have run: " + (double) cpu2.getPriorityQueueTasksNumber() / (double) cpuQueue3.getAllElements() * 100L + "% from queue3 tasks");

        System.out.println("Max queues length:\nqueue1 = " + cpuQueue1.getMaxSize() + "\nqueue2 = " + cpuQueue2.getMaxSize() + "\nqueue3 = " + cpuQueue3.getMaxSize());

    }
}
