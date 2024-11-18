/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenjacobi;

import java.util.ArrayList;

public class Data {
    private ArrayList<Double> xValues = new ArrayList<>();
    private ArrayList<Double> yValues = new ArrayList<>();
    private ArrayList<Double> zValues = new ArrayList<>();
    private ArrayList<Double> errors = new ArrayList<>();

    public void addData(double x, double y, double z, double error) {
        xValues.add(x);
        yValues.add(y);
        zValues.add(z);
        errors.add(error);
    }

    public int getSize() {
        return xValues.size();
    }

    public double getX(int index) {
        return xValues.get(index);
    }

    public double getY(int index) {
        return yValues.get(index);
    }

    public double getZ(int index) {
        return zValues.get(index);
    }

    public double getError(int index) {
        return errors.get(index);
    }
}
