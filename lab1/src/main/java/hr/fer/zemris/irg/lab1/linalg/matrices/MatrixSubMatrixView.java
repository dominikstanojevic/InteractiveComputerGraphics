package hr.fer.zemris.irg.lab1.linalg.matrices;

/**
 * Created by Dominik on 14.3.2017..
 */
public class MatrixSubMatrixView extends AbstractMatrix {
    private IMatrix matrix;
    private int[] rows;
    private int[] columns;

    public MatrixSubMatrixView(IMatrix matrix, int row, int column) {
        this.matrix = matrix;

        int rows = matrix.getRowsCount() - 1;
        int columns = matrix.getColsCount() - 1;

        this.rows = new int[rows];
        this.columns = new int[columns];

        for(int i = 0, index = 0; i <= rows; i++) {
            if (i == row) {
                continue;
            }

            this.rows[index] = i;
            index++;
        }

        for(int i = 0, index = 0; i <= columns; i++) {
            if (i == column) {
                continue;
            }

            this.columns[index] = i;
            index++;
        }
    }

    private MatrixSubMatrixView(IMatrix matrix, int[] rows, int[] columns) {
        this.matrix = matrix;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public int getRowsCount() {
        return rows.length;
    }

    @Override
    public int getColsCount() {
        return columns.length;
    }

    @Override
    public double get(int row, int column) {
       return matrix.get(rows[row], columns[column]);
    }

    @Override
    public IMatrix set(int row, int column, double value) {
        matrix.set(rows[row], columns[column], value);
        return this;
    }

    @Override
    public IMatrix copy() {
        return new MatrixSubMatrixView(matrix, rows, columns);
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMatrix subMatrix(int row, int column, boolean liveView) {
        int nRow = getRowsCount() - 1;
        int nCols = getColsCount() - 1;

        int[] rows = new int[nRow];
        int[] cols = new int[nCols];

        for(int i = 0, iIndex = 0; i <= nRow; i++) {
            if(row == i) {
                continue;
            }

            rows[iIndex] = this.rows[i];
            iIndex++;
        }

        for(int i = 0, jIndex = 0; i <= nCols; i++) {
            if (column == i) {
                continue;
            }

            cols[jIndex] = this.columns[i];
            jIndex++;
        }

        return new MatrixSubMatrixView(matrix, rows, cols);
    }
}
