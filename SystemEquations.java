package com.mycompany.mavenjacobi;

public class SystemEquations {
     private static final double[][] matrix = {
        {1, 2, 3},
        {3, 2, 1},
        {2, 3, 1}
    };

    private static final double[] constants = {9, 13, 10}; // Example constants

    public static Data calculateJacobi(double num1, double num2, double num3) {
 

        // Rearrange to make the matrix diagonally dominant (if possible)
        double[][] rearrangedMatrix = rearrangeDiagonal(matrix);
        if (rearrangedMatrix == null) {
            System.out.println("Matrix cannot be rearranged to diagonal dominance.");
            return null;
        }

        // Initialize Data object for storing results
        Data result = new Data();
        result = iterate(result, 0, 0, 0, true, rearrangedMatrix, constants); // Initial guesses as 0
        return result;
    }

    public static Data calculateGaussSeidel(double num1, double num2, double num3) {

        double[][] rearrangedMatrix = rearrangeDiagonal(matrix);
        if (rearrangedMatrix == null) {
            System.out.println("Matrix cannot be rearranged to diagonal dominance.");
            return null;
        }

        Data result = new Data();
        result = iterate(result, 0, 0, 0, false, rearrangedMatrix, constants); // Initial guesses as 0
        return result;
    }

    private static Data iterate(Data data, double x, double y, double z, boolean isJacobi, double[][] matrix, double[] constants) {
        double new_x, new_y, new_z;
        final int MAX_ITERATIONS = 100;
        int iterationCount = 0;

        while (iterationCount < MAX_ITERATIONS) {
            if (isJacobi) {
                new_x = (constants[0] - (matrix[0][1] * y) - (matrix[0][2] * z)) / matrix[0][0];
                new_y = (constants[1] - (matrix[1][0] * x) - (matrix[1][2] * z)) / matrix[1][1];
                new_z = (constants[2] - (matrix[2][0] * x) - (matrix[2][1] * y)) / matrix[2][2];
            } else {
                new_x = (constants[0] - (matrix[0][1] * y) - (matrix[0][2] * z)) / matrix[0][0];
                new_y = (constants[1] - (matrix[1][0] * new_x) - (matrix[1][2] * z)) / matrix[1][1];
                new_z = (constants[2] - (matrix[2][0] * new_x) - (matrix[2][1] * new_y)) / matrix[2][2];
            }

            // Compute the error (absolute change between iterations)
        double error_x = Math.abs((new_x - x)/new_x);
        double error_y = Math.abs((new_y - y)/new_y);
        double error_z = Math.abs((new_z - z)/new_z);

        double error = (error_x + error_y + error_z)/3;

            data.addData(new_x, new_y, new_z, error);

            if (error <= 0.000000000000001) {
                break; // Convergence criterion
            }

            x = new_x;
            y = new_y;
            z = new_z;
            iterationCount++;
        }

        if (iterationCount >= MAX_ITERATIONS) {
            System.out.println("Maximum iterations reached without convergence.");
        }

        return data;
    }

    public static double[][] rearrangeDiagonal(double[][] matrix) {
        int matrixLength = matrix.length;
        boolean[] usedRows = new boolean[matrixLength];
        double[][] rearrangedMatrix = new double[matrixLength][matrixLength];

        for (int i = 0; i < matrixLength; i++) {
            int bestRow = -1;

            for (int row = 0; row < matrixLength; row++) {
                if (usedRows[row]) {
                    continue;
                }

                double rowSum = 0;
                for (int nonDiag = 0; nonDiag < matrixLength; nonDiag++) {
                    if (nonDiag != i) {
                        rowSum += Math.abs(matrix[row][nonDiag]);
                    }
                }

                if (Math.abs(matrix[row][i]) >= rowSum) {
                    bestRow = row;
                    break;
                }
            }

            if (bestRow == -1) {
                System.out.println("Matrix cannot be rearranged to diagonal dominance.");
                return null;
            }

            rearrangedMatrix[i] = matrix[bestRow];
            usedRows[bestRow] = true;
        }

        return rearrangedMatrix;
    }
}
