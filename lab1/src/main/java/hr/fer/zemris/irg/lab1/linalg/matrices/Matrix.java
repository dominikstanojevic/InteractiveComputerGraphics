package hr.fer.zemris.irg.lab1.linalg.matrices;

import java.util.Arrays;

/**
 * Created by Dominik on 14.3.2017..
 */
public class Matrix extends AbstractMatrix {
    private double[][] elements;

    public Matrix(int rows, int columns) {
        this.elements = new double[rows][columns];
    }

    public Matrix(double[][] elements, boolean copy) {
        if(copy) {
            this.elements = new double[elements.length][];

            for(int i = 0; i < elements.length; i++) {
               this.elements[i] = Arrays.copyOf(elements[i], elements[i].length);
            }
        } else {
            this.elements = elements;
        }
    }

    @Override
    public int getRowsCount() {
        return elements.length;
    }

    @Override
    public int getColsCount() {
        return elements[0].length;
    }

    @Override
    public double get(int row, int column) {
        return elements[row][column];
    }

    @Override
    public IMatrix set(int row, int column, double value) {
        elements[row][column] = value;
        return this;
    }

    @Override
    public IMatrix copy() {
        return new Matrix(elements, true);
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        return new Matrix(rows, columns);
    }

    public static IMatrix parseSimple(String string) {
        String[] rows = string.trim().split("\\|");

        double[][] arr = new double[rows.length][];
        for(int i = 0; i < arr.length; i++) {
            String[] col = rows[i].trim().split("\\s+");
            arr[i] = new double[col.length];

            for(int j = 0; j < col.length; j++) {
                arr[i][j] = Double.parseDouble(col[j].trim());
            }
        }

        return new Matrix(arr, false);
    }
}
