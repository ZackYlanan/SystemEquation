/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenjacobi;

import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Zack
 */
public class Main {
     public static void main(String[] args) {
         // Define the initial coefficient matrix and constants
        double[][] matrix = {
            {1, 2, 3},
            {3, 2, 1},
            {2, 3, 1}
        };
        double[] constants = {9, 13, 10}; // Example constants

        // Print the original matrix
        System.out.println("Original Matrix:");
        printMatrix(matrix);

        // Rearrange to make the matrix diagonally dominant
        double[][] rearrangedMatrix = SystemEquations.rearrangeDiagonal(matrix);

        if (rearrangedMatrix == null) {
            System.out.println("The matrix cannot be rearranged to a diagonally dominant form.");
            return;
        }

        System.out.println("\nRearranged Matrix:");
        printMatrix(rearrangedMatrix);
        
        Data jacobiDataSet = SystemEquations.calculateJacobi(0, 0, 0);

        Data gaussDataSet = SystemEquations.calculateGaussSeidel(0, 0, 0);

        Graph graph = new Graph(jacobiDataSet, gaussDataSet);

        printData(jacobiDataSet, "Jacobi");
        printData(gaussDataSet, "Gauss-Seidel");
    }

    public static void printData(Data data, String title) {
        System.out.println("\n==================================\n\n" + title + ":\n");
        for(int i = 0 ; i < data.getSize() ; i++) {
            System.out.printf("K = %d | E = %.15f | x = %.6f | y = %.6f | z = %.6f\n", (i+1), data.getError(i), data.getX(i), data.getY(i), data.getZ(i));
        }
    }
    public static void printMatrix(double[][] matrix) {
    for(int row = 0; row < matrix.length; row++) { // Loop through each row
        for (int col = 0; col < matrix[row].length; col++) { // Loop through each column in the row
            System.out.print(matrix[row][col] + " "); // Print the value with a space
        }
        System.out.println(); // Move to the next line after printing each row
    }
}
}
