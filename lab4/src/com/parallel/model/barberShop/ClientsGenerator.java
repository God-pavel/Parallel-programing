package com.parallel.model.barberShop;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.random;

public class ClientsGenerator extends Thread {
    private final BarberShop barberShop;
    private final int maxInterval;
    private final int minInterval;
    private final int clientsToGenerate;

    private final ReentrantLock clientLock;
    private final ReentrantLock barberLock;
    private final ReentrantLock serviceLock;

    private final Condition clientReady;
    private final Condition barberReady;
    private final Condition barberFinished;

    public ClientsGenerator(final int maxInterval,
                            final int minInterval,
                            final int clientsToGenerate,
                            final BarberShop barberShop,
                            final ReentrantLock clientLock,
                            final ReentrantLock barberLock,
                            final ReentrantLock serviceLock,
                            final Condition clientReady,
                            final Condition barberReady,
                            final Condition barberFinished) {
        this.maxInterval = maxInterval;
        this.minInterval = minInterval;
        this.clientsToGenerate = clientsToGenerate;
        this.barberShop = barberShop;
        this.clientLock = clientLock;
        this.barberLock = barberLock;
        this.serviceLock = serviceLock;
        this.clientReady = clientReady;
        this.barberReady = barberReady;
        this.barberFinished = barberFinished;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < clientsToGenerate) {
            Client client = new Client(barberShop, clientLock, barberLock, serviceLock, clientReady, barberReady, barberFinished);
            client.start();
            i++;
            try {
                sleep(minInterval + (int) (random() * (maxInterval - minInterval)));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Max queue size: " + barberShop.getMaxAmountInQueue());
        }
    }
}
