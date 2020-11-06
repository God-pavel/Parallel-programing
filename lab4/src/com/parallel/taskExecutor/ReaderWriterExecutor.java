package com.parallel.taskExecutor;

import com.parallel.model.readersWriters.ReaderWriterStorage;
import com.parallel.model.Storage;
import com.parallel.model.consumerSupplier.Consumer;
import com.parallel.model.consumerSupplier.Supplier;

import static java.lang.Thread.sleep;

public class ReaderWriterExecutor implements Executor {

    @Override
    public void execute() {
        final int tasksToCreate = 10;
        final Storage storage = new ReaderWriterStorage();
        final Consumer consumer1 = new Consumer(storage);
        final Consumer consumer2 = new Consumer(storage);
        final Consumer consumer3 = new Consumer(storage);
        final Supplier supplier1 = new Supplier(200, 10, 300, 200, tasksToCreate, storage);
        final Supplier supplier2 = new Supplier(400, 30, 1000, 500, tasksToCreate, storage);

        supplier1.start();
        supplier2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();

        while (true) {
            int completedTasks = consumer1.getCompletedTasks() + consumer2.getCompletedTasks() + consumer3.getCompletedTasks();
            if (completedTasks > 150) {
                consumer1.interrupt();
                consumer2.interrupt();
                consumer3.interrupt();
                break;
            }
        }
    }
}
