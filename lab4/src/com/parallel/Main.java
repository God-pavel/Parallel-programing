package com.parallel;

import com.parallel.taskExecutor.*;

public class Main {

    public static void main(String[] args) {
//        final Executor supplierConsumer = new SupplierConsumerExecutor();
//        supplierConsumer.execute();

        final Executor readerWriter = new ReaderWriterExecutor();
        readerWriter.execute();

//        final Executor philosophersExecutor = new PhilosophersExecutor();
//        philosophersExecutor.execute();

//        final Executor barberShopExecutor = new BarberShopExecutor();
//        barberShopExecutor.execute();
    }
}
