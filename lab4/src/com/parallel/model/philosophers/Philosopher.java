package com.parallel.model.philosophers;

import java.util.List;
import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private int eatCount = 0;
    private final int id;
    private final Semaphore fork1;
    private final Semaphore fork2;

    public Philosopher(final int id, final List<Semaphore> forks) {
        this.id = id;
        if (id == 4) {
            fork1 = forks.get(0);
            fork2 = forks.get(4);
        } else {
            fork1 = forks.get(id);
            fork2 = forks.get(id + 1);
        }
    }

    public final void run() {
        try {
            while (eatCount < 3) {
                fork1.acquire();
                Thread.sleep(500);
                fork2.acquire();
                System.out.println("Philosopher " + id + " is eating...");
                sleep(3000);
                eatCount++;

                System.out.println("Philosopher " + id + " has eaten");
                fork1.release();
                fork2.release();
                System.out.println("Philosopher " + id + " is thinking...");
                sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}