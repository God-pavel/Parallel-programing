package com.parallel.taskExecutor;

import com.parallel.model.philosophers.Philosopher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class PhilosophersExecutor implements Executor {

    @Override
    public final void execute() {
        final List<Semaphore> forks = new ArrayList<>();
        final List<Philosopher> philosophers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            forks.add(new Semaphore(1));
        }
        for (int i = 0; i < 5; i++) {
            philosophers.add(new Philosopher(i, forks));
        }
        philosophers.forEach(Thread::start);
    }

}
