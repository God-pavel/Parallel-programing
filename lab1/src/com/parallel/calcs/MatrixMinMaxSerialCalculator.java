package com.parallel.calcs;

public class MatrixMinMaxSerialCalculator extends MatrixMinMaxCalculator {

    public MatrixMinMaxSerialCalculator(final int[][] matrix, final int n, final int m) {
        this.matrix = matrix;
        this.n = n;
        this.m = m;
    }

    @Override
    public void calcMinMaxValues() {
        long startTime = System.nanoTime();
        this.maxValue = matrix[0][0];
        this.minValue = matrix[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.maxValue = Math.max(matrix[i][j], maxValue);
                this.minValue = Math.min(matrix[i][j], minValue);
            }
        }
        long endTime = System.nanoTime();
        this.time = endTime - startTime;
    }
}
