package com.company.course_work;


public class Main {
    public static void main(String[] args) {

        System.out.println("Serial approximate solve:");
        Dyffur dyffur = new Dyffur();

        long beginTimeSerial = System.nanoTime();
        double[][] approximateResult = new SerialSolution(dyffur).solve();
        long endTimeSerial = System.nanoTime();
        long serialTime = endTimeSerial - beginTimeSerial;
        printMatrix(approximateResult, dyffur);
        System.out.println("Time of serial solve: " + serialTime);
        System.out.println();


        System.out.println("Parallel solve");
        long beginTimeParallel = System.nanoTime();
        double[][] parallelSolution = new ParallelSolution(dyffur).solve();
        long endTimeParallel = System.nanoTime();
        long parallelTime = endTimeParallel - beginTimeParallel;
        printMatrix(parallelSolution, dyffur);
        System.out.println("Time of parallel solve: " + parallelTime + " ns");

        System.out.println("Exact solve:");
        printErrors(approximateResult, calculateExactResult(dyffur), dyffur);
    }

    private static double[][] calculateExactResult(Dyffur dyffur) {
        double[][] exactMatrix = new double[dyffur.getTpointsAmount()][dyffur.getHpointsAmount()];
        double t = dyffur.getT0();
        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            double x = dyffur.getX0();
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                exactMatrix[i][j] = dyffur.calculateTrueSolution(x, t);
                x += dyffur.getH();
            }
            t += dyffur.getTau();
        }

        System.out.println();

        for (int i = 0; i < dyffur.getTpointsAmount(); ++i) {
            for (int j = 0; j < dyffur.getHpointsAmount(); ++j) {
                System.out.print(String.format("%.5f\t", exactMatrix[i][j]));
            }
            System.out.println();
        }

        return exactMatrix;
    }

    private static void printMatrix(double[][] matrix, Dyffur dyffur) {
        for (int i = 0; i < dyffur.getTpointsAmount(); ++i) {
            for (int j = 0; j < dyffur.getHpointsAmount(); ++j) {
                System.out.print(String.format("%.5f\t", matrix[i][j]));
            }
            System.out.println();

        }
    }



    private static void printErrors(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        System.out.println("AverageAbsoluteError: " + averageAbsoluteError(approximateResult, exactMatrix, dyffur));
        System.out.println("MaxAbsoluteError: " + maxAbsoluteError(approximateResult, exactMatrix, dyffur));
        System.out.println("AverageRelativeError: " + averageRelativeError(approximateResult, exactMatrix, dyffur));
        System.out.println("MaxRelativeError: " + maxRelativeError(approximateResult, exactMatrix, dyffur));
    }


    private static double averageAbsoluteError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTpointsAmount()][dyffur.getHpointsAmount()];
        double error = 0;
        double errorResult = 0;

        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                matrix[i][j] = Math.abs(approximateResult[i][j] - exactMatrix[i][j]);
            }
        }

        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                error += matrix[i][j];
            }
        }
        errorResult = error / (dyffur.getTpointsAmount() * dyffur.getHpointsAmount());

        return errorResult;
    }

    private static double maxAbsoluteError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTpointsAmount()][dyffur.getHpointsAmount()];
        double maxError = 0;

        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                matrix[i][j] = Math.abs(approximateResult[i][j] - exactMatrix[i][j]);
            }
        }

        maxError = matrix[0][0];
        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                if (maxError < matrix[i][j]) {
                    maxError = matrix[i][j];
                }
            }
        }


        return maxError;
    }


    private static double averageRelativeError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTpointsAmount()][dyffur.getHpointsAmount()];
        double error = 0;
        double errorResult = 0;

        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                if (exactMatrix[i][j] == 0) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = (Math.abs(approximateResult[i][j] - exactMatrix[i][j]) / exactMatrix[i][j]) * 100;
                }
                error += matrix[i][j];
            }
        }


        errorResult = error / (dyffur.getTpointsAmount() * dyffur.getHpointsAmount());

        return errorResult;
    }

    private static double maxRelativeError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTpointsAmount()][dyffur.getHpointsAmount()];
        double maxError = 0;

        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                if (exactMatrix[i][j] == 0) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = (Math.abs(approximateResult[i][j] - exactMatrix[i][j]) / exactMatrix[i][j]) * 100;
                }
            }
        }

        maxError = matrix[0][0];
        for (int i = 0; i < dyffur.getTpointsAmount(); i++) {
            for (int j = 0; j < dyffur.getHpointsAmount(); j++) {
                if (maxError < matrix[i][j]) {
                    maxError = matrix[i][j];
                }
            }
        }


        return maxError;
    }


}
