package com.parallel.model;

import com.parallel.model.consumerSupplier.Task;

public interface Storage {
    void put(final Task task);
    Task getTask();
}
