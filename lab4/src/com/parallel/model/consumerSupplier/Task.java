package com.parallel.model.consumerSupplier;

import static java.lang.Math.random;

public class Task {

    private final int duration;

    public Task(final int maxDuration, final int minDuration) {
        this.duration = minDuration + (int) (random() * (maxDuration - minDuration));
    }

    public final int getDuration() {
        return duration;
    }
}

