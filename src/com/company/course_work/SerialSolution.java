package com.company.course_work;

public class SerialSolution{

    private int tPointsAmount;
    private int hPointsAmount;
    private double x0;
    private double h;
    private double t0;
    private double tau;
    private Dyffur dyffur;

    public SerialSolution(Dyffur dyffur) {
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
            for (int j = 1; j < hPointsAmount - 1; j++) {
                w[i][j] = dyffur.calculateApproximateSolution(w[i-1][j-1],w[i-1][j],w[i-1][j+1]);
            }
            w[i][hPointsAmount - 1] = dyffur.calculateRightBorder(t);
        }
        return w;
    }
}
