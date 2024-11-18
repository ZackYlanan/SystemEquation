/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenjacobi;

/**
 *
 * @author Zack
 */

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Graph {
    final int WIDTH = 800;
    final int HEIGHT = 550;
    
    public Graph(Data jacobi, Data gauss) {
        JFrame frame = new JFrame("System of Equations: Graphs");

        //  Window Preparation
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChartPanel chart1 = createLineChart(jacobi, true, "Jacobi Series", "Jacobi: k = " + jacobi.getSize());
        ChartPanel chart2 = createLineChart(gauss, false, "Gauss-Seidel Series", "Gauss-Seidel: k = " + gauss.getSize());
        ChartPanel chart3 = createLineChart(jacobi, gauss, "Jacobi Series","Gauss-Seidel Series", "Jacobi ("+jacobi.getSize()+") vs Gauss-Seidel ("+gauss.getSize()+")");

        JPanel chart1Tab = new JPanel();
        JPanel chart2Tab = new JPanel();
        JPanel chart3Tab = new JPanel();

        chart1Tab.add(chart1);
        chart2Tab.add(chart2);
        chart3Tab.add(chart3);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Jacobi", chart1Tab);
        tabs.add("Gauss-Seidel", chart2Tab);
        tabs.add("Jacobi & Gauss-Seidel", chart3Tab);

        frame.add(tabs);
        frame.setVisible(true);
    }

    private ChartPanel createLineChart(Data data1, Data data2, String series1, String series2, String title) {
        XYSeries seriesA = new XYSeries(series1);
        XYSeries seriesB = new XYSeries(series2);

        // Add data points for both series
        for (int i = 0; i < data1.getSize(); i++) {
            seriesA.add(i + 1, data1.getError(i));
        }
        for (int i = 0; i < data2.getSize(); i++) {
            seriesB.add(i + 1, data2.getError(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesA);
        dataset.addSeries(seriesB);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Iteration",
                "Error %",
                dataset
        );

        // Customize plot to add dots for both series
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesShapesVisible(0, true); // Enable shapes (dots) for series 0
        renderer.setSeriesLinesVisible(0, true); // Enable lines for series 0
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0)); // Circle
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesShapesVisible(1, true); // Enable shapes (dots) for series 1
        renderer.setSeriesLinesVisible(1, true); // Enable lines for series 1
        renderer.setSeriesShape(1, new java.awt.geom.Rectangle2D.Double(-3.0, -3.0, 6.0, 6.0)); // Square
        renderer.setSeriesPaint(1, Color.blue);

        plot.setRenderer(renderer);
        plot.setDomainAxis(new NumberAxis("Iteration"));
        plot.setRangeAxis(new NumberAxis("Error %"));

        return new ChartPanel(chart);
    }

    private ChartPanel createLineChart(Data data, boolean isJacobi, String seriesName, String title) {
        XYSeries series = new XYSeries(seriesName);

        // Add data points
        for (int i = 0; i < data.getSize(); i++) {
            series.add(i + 1, data.getError(i)); // x = iteration, y = error
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Iteration",
                "Error %",
                dataset
        );

        // Customize plot to add dots
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true); // Enable shapes (dots) for series 0
        renderer.setSeriesLinesVisible(0, true); // Keep lines visible

        Color color = (isJacobi)?Color.red :Color.BLUE;

        // Set shape (e.g., a circle)
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0)); // 6x6 circle
        renderer.setSeriesPaint(0, color); // Optional: Set color for the series

        plot.setRenderer(renderer);
        plot.setDomainAxis(new NumberAxis("Iteration"));
        plot.setRangeAxis(new NumberAxis("Error %"));

        return new ChartPanel(chart);
    }
}
