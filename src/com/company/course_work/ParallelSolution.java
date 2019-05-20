package com.company.course_work;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelSolution {

    private int tPointsAmount;
    private int hPointsAmount;
    private double x0;
    private double h;
    private double t0;
    private double tau;
    private Dyffur dyffur;

    public ParallelSolution(Dyffur dyffur) {
        this.dyffur = dyffur;
        this.tPointsAmount = dyffur.getTpointsAmount();
        this.hPointsAmount = dyffur.getHpointsAmount();
        this.x0 = dyffur.getX0();
        this.h = dyffur.getH();
        this.t0 = dyffur.getT0();
        this.tau = dyffur.getTau();
    }

    public double[][] solve() {
        double t = t0 + tau;
        double x = x0;
        double[][] w = new double[tPointsAmount][hPointsAmount];
        for (int j = 0; j < hPointsAmount; j++, x += h) {
            w[0][j] = dyffur.calculateBottomBorder(x);
        }

        for (int i = 1; i < tPointsAmount; ++i, t += tau) {
            w[i][0] = dyffur.calculateLeftBorder(t);

            int finalI = i;

            IntStream.range(1, dyffur.getHpointsAmount() - 1).parallel().forEach(j -> {
                w[finalI][j] = dyffur.calculateApproximateSolution(w[finalI - 1][j - 1], w[finalI - 1][j], w[finalI - 1][j + 1]);

            });

            w[i][hPointsAmount - 1] = dyffur.calculateRightBorder(t);
        }
        return w;
    }

}
