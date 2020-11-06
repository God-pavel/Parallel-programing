package com.parallel.model.barberShop;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Client extends Thread {

    private final BarberShop barberShop;

    private final ReentrantLock clientLock;
    private final ReentrantLock barberLock;
    private final ReentrantLock serviceLock;

    private final Condition clientReady;
    private final Condition barberReady;
    private final Condition barberFinished;

    public Client(final BarberShop barberShop,
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

        if (barberShop.addClient(this)) {
            System.out.println("Client has come");
            signalBarber();
            waitBarber();
            makingHaircut();
            System.out.println("Client go home");
        } else {
            goHome();
        }
    }

    private void waitBarber() {
        barberLock.lock();
        try {
            barberReady.await();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            barberLock.unlock();
        }
    }

    private void makingHaircut() {
        serviceLock.lock();
        try {
            barberFinished.await();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            serviceLock.unlock();
        }
    }

    private void signalBarber() {
        clientLock.lock();
        try {
            clientReady.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientLock.unlock();
        }
    }

    public final void goHome() {
        System.out.println("Queue is full");
        Thread.currentThread().interrupt();
    }
}
