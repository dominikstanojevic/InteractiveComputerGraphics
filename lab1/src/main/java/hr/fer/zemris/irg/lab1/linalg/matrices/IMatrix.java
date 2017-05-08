package hr.fer.zemris.irg.lab1.linalg.matrices;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;

/**
 * Created by Dominik on 14.3.2017..
 */
public interface IMatrix {
    int getRowsCount();
    int getColsCount();
    double get(int row, int column);
    IMatrix set(int row, int column, double value);
    IMatrix copy();
    IMatrix newInstance(int rows, int columns);
    IMatrix nTranspose(boolean liveView);
    IMatrix add(IMatrix other);
    IMatrix nAdd(IMatrix other);
    IMatrix sub(IMatrix other);
    IMatrix nSub(IMatrix other);
    IMatrix scalarMultiply(double scalar);
    IMatrix nScalarMultiply(double scalar);
    IMatrix nMultiply(IMatrix other);
    double determinant();
    IMatrix subMatrix(int row, int column, boolean liveView);
    IMatrix nInvert();
    double[][] toArray();
    String toString(int precision);
    IVector toVector(boolean liveView);
}
