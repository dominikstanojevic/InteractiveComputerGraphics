package hr.fer.zemris.irg.lab1.linalg.vectors;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;

/**
 * Created by Dominik on 14.3.2017..
 */
public class VectorMatrixView extends AbstractVector {
    private IMatrix matrix;
    private boolean rowMatrix;

    public VectorMatrixView(IMatrix matrix) {
        if (matrix.getRowsCount() == 1) {
            this.rowMatrix = true;
        } else if (matrix.getColsCount() == 1) {
            this.rowMatrix = false;
        } else {
            throw new RuntimeException("Matrix dimension does not match.");
        }

        this.matrix = matrix;
    }

    @Override
    public double get(int index) {
        return rowMatrix ? matrix.get(0, index) : matrix.get(index, 0);
    }

    @Override
    public IVector set(int index, double value) {
        if(rowMatrix) {
            matrix.set(0, index, value);
        } else {
            matrix.set(index, 0, value);
        }

        return this;
    }

    @Override
    public int getDimension() {
        return rowMatrix ? matrix.getColsCount() : matrix.getRowsCount();
    }

    @Override
    public IVector copy() {
        return new VectorMatrixView(matrix);
    }

    @Override
    public IVector newInstance(int length) {
        return new Vector(length);
    }
}
