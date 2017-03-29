package hr.fer.zemris.irg.lab1.linalg.matrices;

/**
 * Created by Dominik on 14.3.2017..
 */
public class MatrixTransposeView extends AbstractMatrix {
    private IMatrix matrix;

    public MatrixTransposeView(IMatrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public int getRowsCount() {
        return matrix.getColsCount();
    }

    @Override
    public int getColsCount() {
        return matrix.getRowsCount();
    }

    @Override
    public double get(int row, int column) {
        return matrix.get(column, row);
    }

    @Override
    public IMatrix set(int row, int column, double value) {
        set(column, row, value);
        return this;
    }

    @Override
    public IMatrix copy() {
        return new MatrixTransposeView(matrix);
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        return new Matrix(rows, columns);
    }
}
