package com.parallel.taskExecutor;

import com.parallel.model.barberShop.Barber;
import com.parallel.model.barberShop.BarberShop;
import com.parallel.model.barberShop.ClientsGenerator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopExecutor implements Executor {

    @Override
    public final void execute() {
        final BarberShop barberShop = new BarberShop();

        final ReentrantLock clientLock = new ReentrantLock();
        final ReentrantLock barberLock = new ReentrantLock();
        final ReentrantLock serviceLock = new ReentrantLock();

        final Condition clientReady = clientLock.newCondition();
        final Condition barberReady = barberLock.newCondition();
        final Condition barberFinished = serviceLock.newCondition();

        final Barber barber = new Barber(barberShop, clientLock, barberLock, serviceLock, clientReady, barberReady, barberFinished);
        final ClientsGenerator clientsGenerator = new ClientsGenerator(1000, 2000, 10, barberShop, clientLock, barberLock, serviceLock, clientReady, barberReady, barberFinished);

        barber.start();
        clientsGenerator.start();
    }
}
