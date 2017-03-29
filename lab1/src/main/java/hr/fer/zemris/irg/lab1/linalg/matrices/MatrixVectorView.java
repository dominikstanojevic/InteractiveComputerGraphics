package hr.fer.zemris.irg.lab1.linalg.matrices;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;

/**
 * Created by Dominik on 14.3.2017..
 */
public class MatrixVectorView extends AbstractMatrix {
    private IVector vector;
    private boolean asRowMatrix;

    public MatrixVectorView(IVector vector, boolean asRowMatrix) {
        this.vector = vector;
        this.asRowMatrix = asRowMatrix;
    }

    @Override
    public int getRowsCount() {
       return asRowMatrix ? 1 : vector.getDimension();
    }

    @Override
    public int getColsCount() {
        return asRowMatrix ? vector.getDimension() : 1;
    }

    @Override
    public double get(int row, int column) {
        if (asRowMatrix) {
            if(row != 0) {
                throw new RuntimeException("Invalid row index.");
            }

            return vector.get(column);
        } else {
            if(column != 0) {
                throw new RuntimeException("Invalid column index.");
            }

            return vector.get(row);
        }
    }

    @Override
    public IMatrix set(int row, int column, double value) {
        if (asRowMatrix) {
            if(row != 0) {
                throw new RuntimeException("Invalid row index.");
            }

            vector.set(column, value);
        } else {
            if(column != 0) {
                throw new RuntimeException("Invalid column index.");
            }

            vector.set(row, value);
        }

        return this;
    }

    @Override
    public IMatrix copy() {
        return new MatrixVectorView(vector, asRowMatrix);
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        return new Matrix(rows, columns);
    }
}
