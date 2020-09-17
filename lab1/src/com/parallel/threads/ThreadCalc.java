package com.parallel.threads;

public class ThreadCalc extends Thread {

    private final int[][] matrix;
    private final int startRow;
    private final int endRow;
    private final int columns;
    private int minValue;
    private int maxValue;

    public ThreadCalc(final int[][] matrix,
                      final int startRow,
                      final int endRow,
                      final int columns) {
        this.matrix = matrix;
        this.startRow = startRow;
        this.endRow = endRow;
        this.columns = columns;
    }

    @Override
    public void run() {
        this.maxValue = matrix[startRow][0];
        this.minValue = matrix[endRow - 1][0];
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < columns; j++) {
                this.maxValue = Math.max(matrix[i][j], maxValue);
                this.minValue = Math.min(matrix[i][j], minValue);
            }
        }
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
