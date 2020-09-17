package com.parallel;

import com.parallel.calcs.MatrixMinMaxCalculator;
import com.parallel.calcs.MatrixMinMaxParallelCalculator;
import com.parallel.calcs.MatrixMinMaxSerialCalculator;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int n = 20000;
        int m = 20000;
        int jobs = 4;
        int[][] matrix = createMatrix(n, m);

        System.out.println("---Послідовне виконання---");
        MatrixMinMaxCalculator serialCalculator = new MatrixMinMaxSerialCalculator(matrix, n, m);
        serialCalculator.calcMinMaxValues();
        serialCalculator.printResult();

        System.out.println("---Паралельне виконання---");
        MatrixMinMaxCalculator parallelCalculator = new MatrixMinMaxParallelCalculator(matrix, n, m, jobs);
        parallelCalculator.calcMinMaxValues();
        parallelCalculator.printResult();

        double acceleration = (double) serialCalculator.getTime()/ parallelCalculator.getTime();

        System.out.println("Коефіцієнт прискорення: " + acceleration);
        System.out.println("Коеіфцієнт ефективності: " + acceleration/jobs);

    }

    private static int[][] createMatrix(final int rows, final int columns) {
        int[][] array = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = (int) (Math.random() * 1000000000);
            }
        }
        return array;
    }
}
