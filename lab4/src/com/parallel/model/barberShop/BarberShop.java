package com.parallel.model.barberShop;

import java.util.concurrent.locks.ReentrantLock;

public class BarberShop {

    private int queue = 0;
    private int maxAmountInQueue = 0;
    private final int maxSize = 5;
    private final ReentrantLock queueLock = new ReentrantLock();

    public final boolean addClient(final Client client) {
        queueLock.lock();
        try {
            if (queue >= maxSize) {
                return false;
            } else {
                queue++;
                if (queue > maxAmountInQueue) {
                    maxAmountInQueue = queue;
                }
                return true;
            }
        } finally {
            queueLock.unlock();
        }
    }

    public final boolean isClientsPresent() {
        queueLock.lock();
        try {
            return queue > 0;
        } finally {
            queueLock.unlock();
        }
    }

    public final void getFirstClientFromTheQueue() {
        queueLock.lock();
        try {
            queue--;
        } finally {
            queueLock.unlock();
        }
    }

    public int getMaxAmountInQueue() {
        return maxAmountInQueue;
    }
}
