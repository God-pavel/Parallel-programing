package com.parallel.model;

import static java.lang.Math.random;

public class CPUTask {

    private final int duration;

    public CPUTask(final int maxDuration, final int minDuration) {
        this.duration = minDuration + (int) (random() * (maxDuration - minDuration));
    }

    public final int getDuration() {
        return duration;
    }
}
