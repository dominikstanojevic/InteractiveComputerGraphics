package hr.fer.zemris.irg.lab1.linalg.vectors;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;

/**
 * Created by Dominik on 13.3.2017..
 */
public interface IVector {
    double get(int index);
    IVector set(int index, double value);
    int getDimension();
    IVector copy();
    IVector copyPart(int start, int length);
    IVector newInstance(int length);
    IVector add(IVector other);
    IVector nAdd(IVector other);
    IVector sub(IVector other);
    IVector nSub(IVector other);
    IVector scalarMultiply(double scalar);
    IVector nScalarMultiply(double scalar);
    double norm();
    IVector normalize();
    IVector nNormalize();
    double cosine(IVector other);
    double scalarProduct(IVector other);
    IVector nVectorProduct(IVector other);
    IVector nFromHomogeneous();
    IMatrix toRowMatrix();
    IMatrix toColumnMatrix();
    double[] toArray();
    String toString(int precision);
}
