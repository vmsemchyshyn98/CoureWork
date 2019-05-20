package com.company.course_work;

public class Dyffur {

    private double x0 = 0;
    private double x1 = 1;
    private double h = 1.0 / 30;
    private double t0 = 0;
    private double t1 = 1;
    private double tau = 1.0 / 900;
    private double a = 1;
    private double A = 0;
    private double B = 4;

    public double getX0() {
        return x0;
    }

    public double getH() {
        return h;
    }

    public double getT0() {
        return t0;
    }

    public double getTau() {
        return tau;
    }

    public int getTpointsAmount() {
        return (int) Math.ceil((t1 - t0) / tau) + 1;
    }

    public int getHpointsAmount() {
        return (int) Math.ceil((x1 - x0) / h) + 1;
    }

    public double calculateTrueSolution(double x, double t) {
        return Math.sqrt(Math.pow(x - A, 2) / (4 * a * (B - t)));
    }

    public double calculateBottomBorder(double x) {
        double t = 0;
        return Math.sqrt(Math.pow(x - A, 2) /(4 * a * (B - t)) );
    }

    public double calculateLeftBorder(double t) {
        double x = 0;
        return Math.sqrt(Math.pow(x - A, 2) / (4 * a * (B - t)));
    }

    public double calculateRightBorder(double t) {
        double x = 1;
        return Math.sqrt(Math.pow(x - A, 2) / (4 * a * (B - t)));
    }

    public double calculateApproximateSolution(double wiLeft, double wiCurrent, double wiRight) {
        double sigma = tau / (h * h);
        return (wiCurrent + (a*sigma/2) * ((wiCurrent * Math.pow(wiRight - wiLeft, 2)) + 2* Math.pow(wiCurrent, 2) * (wiLeft - 2 * wiCurrent + wiRight)));
    }


}
