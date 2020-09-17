package com.parallel.calcs;

public abstract class MatrixMinMaxCalculator {
    protected int[][] matrix;
    protected int n;
    protected int m;
    protected int minValue;
    protected int maxValue;
    protected long time;

    public abstract void calcMinMaxValues() throws InterruptedException;

    public void printResult() {
        System.out.println("Max number: " + this.maxValue);
        System.out.println("Min number: " + this.minValue);
        System.out.println("Total calculation time : " + this.time + " ns." + "\n");
    }
    public long getTime(){
        return time;
    }
}
