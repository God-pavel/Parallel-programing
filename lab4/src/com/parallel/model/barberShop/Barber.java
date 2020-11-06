package com.parallel.model.barberShop;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barber extends Thread {
    private final BarberShop barberShop;
    private final ReentrantLock clientLock;
    private final ReentrantLock barberLock;
    private final ReentrantLock serviceLock;

    private final Condition clientReady;
    private final Condition barberReady;
    private final Condition barberFinished;

    public Barber(final BarberShop barberShop,
                  final ReentrantLock clientLock,
                  final ReentrantLock barberLock,
                  final ReentrantLock serviceLock,
                  final Condition clientReady,
                  final Condition barberReady,
                  final Condition barberFinished) {
        this.barberShop = barberShop;
        this.clientLock = clientLock;
        this.barberLock = barberLock;
        this.serviceLock = serviceLock;
        this.clientReady = clientReady;
        this.barberReady = barberReady;
        this.barberFinished = barberFinished;
    }

    @Override
    public final void run() {
        while (!isInterrupted()) {
            if (!barberShop.isClientsPresent()) {
                waitForClients();
            }
            barberReady();
            barberShop.getFirstClientFromTheQueue();

            makeHaircut();
        }
    }


    private void waitForClients() {
        clientLock.lock();
        try {
            System.out.println("Barber is waiting ...");
            clientReady.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            clientLock.unlock();
        }
    }

    private void barberReady() {
        barberLock.lock();
        try {
            barberReady.signalAll();
        } finally {
            barberLock.unlock();
        }
    }

    private void makeHaircut() {
        serviceLock.lock();
        try {
            System.out.println("Barber is working ...");
            sleep(2000);
            barberFinished.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serviceLock.unlock();
        }
    }
}
