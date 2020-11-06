package com.parallel.taskExecutor;

import com.parallel.model.Storage;
import com.parallel.model.consumerSupplier.Consumer;
import com.parallel.model.consumerSupplier.ConsumerSupplierStorage;
import com.parallel.model.consumerSupplier.Supplier;

public class SupplierConsumerExecutor implements Executor {

    @Override
    public void execute() {
        final int tasksToComplete = 10;
        final Storage CSStorage = new ConsumerSupplierStorage(5);
        final Consumer consumer = new Consumer(CSStorage);
        final Supplier supplier = new Supplier(2000, 1000, 300, 200, tasksToComplete, CSStorage);

        supplier.start();
        consumer.start();
        while (true) {
            int completedTasks = consumer.getCompletedTasks();
            if (completedTasks == tasksToComplete) {
                consumer.interrupt();
                break;
            }
        }
    }
}
