package com.parallel.calcs;

import com.parallel.threads.ThreadCalc;

public class MatrixMinMaxParallelCalculator extends MatrixMinMaxCalculator {

    private final int jobsNumber;

    public MatrixMinMaxParallelCalculator(final int[][] matrix,
                                          final int n,
                                          final int m,
                                          final int jobsNumber) {
        this.matrix = matrix;
        this.n = n;
        this.m = m;
        this.jobsNumber = jobsNumber;
    }

    @Override
    public void calcMinMaxValues() throws InterruptedException {
        long startTime = System.nanoTime();
        this.maxValue = matrix[0][0];
        this.minValue = matrix[0][0];

        ThreadCalc[] jobs = new ThreadCalc[jobsNumber];
        for (int i = 0; i < jobsNumber; i++) {
            if (i == jobsNumber - 1) {
                jobs[i] = new ThreadCalc(matrix, n / jobsNumber * i, n / jobsNumber * (i + 1) + n % jobsNumber, m);
            } else {
                jobs[i] = new ThreadCalc(matrix, n / jobsNumber * i, n / jobsNumber * (i + 1), m);
            }
            jobs[i].start();
        }
        for (int i = 0; i < jobsNumber; i++) {
            jobs[i].join();
        }

        for (int i = 0; i < jobsNumber; i++) {
            this.maxValue = Math.max(jobs[i].getMaxValue(), this.maxValue);
            this.minValue = Math.min(jobs[i].getMinValue(), this.minValue);
        }

        long endTime = System.nanoTime();
        this.time = endTime - startTime;
    }
}
