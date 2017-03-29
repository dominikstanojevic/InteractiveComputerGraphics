package hr.fer.zemris.irg.lab1.linalg.matrices;

import java.util.Locale;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Dominik on 14.3.2017..
 */
public abstract class AbstractMatrix implements IMatrix {
    @Override
    public IMatrix nTranspose(boolean liveView) {
        if (liveView) {
            return new MatrixTransposeView(this);
        } else {
            int rows = getColsCount();
            int cols = getRowsCount();
            IMatrix transpose = newInstance(rows, cols);
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    double value = get(j, i);
                    transpose.set(i, j, value);
                }
            }

            return transpose;
        }
    }

    @Override
    public IMatrix add(IMatrix other) {
        elementWiseBinaryOperation(this, other, (a, b) -> a + b);
        return this;
    }

    @Override
    public IMatrix nAdd(IMatrix other) {
        IMatrix copy = copy();
        return copy.add(other);
    }

    @Override
    public IMatrix sub(IMatrix other) {
        elementWiseBinaryOperation(this, other, (a, b) -> a - b);
        return this;
    }

    @Override
    public IMatrix nSub(IMatrix other) {
        IMatrix copy = copy();
        return copy.sub(other);
    }

    @Override
    public IMatrix scalarMultiply(double scalar) {
        elementWiseUnaryOperation(this, v -> v * scalar);
        return this;
    }

    @Override
    public IMatrix nScalarMultiply(double scalar) {
        IMatrix copy = copy();
        return copy.scalarMultiply(scalar);
    }

    @Override
    public IMatrix nMultiply(IMatrix other) {
       if(getColsCount() != other.getRowsCount()) {
           throw new RuntimeException("Invalid matrices.");
       }

       int m = getColsCount();       int rows = getRowsCount();
       int cols = other.getColsCount();
       IMatrix mul = newInstance(rows, cols);

       for(int i = 0; i < rows; i++) {
           for(int j = 0; j < cols; j++) {
               double sum = 0;
               for(int k = 0; k < m; k++) {
                   sum += get(i, k) * other.get(k, j);
               }

               mul.set(i, j, sum);
           }
       }

       return mul;
    }

    @Override
    public double determinant() {
        if (getRowsCount() != getColsCount()) {
            throw new RuntimeException("Cannot get determinant of non-square matrix");
        }

        int order = getRowsCount();
        if(order == 1) {
            return get(0, 0);
        }

        double determinant = 0;
        int sign = 1;

        for(int i = 0; i < order; i++) {
            IMatrix cofactor = subMatrix(0, i, true);
            determinant += sign * get(0, i) * cofactor.determinant();

            sign *= -1;
        }

        return determinant;
    }

    @Override
    public IMatrix nInvert() {
        double determinant = determinant();
        if(determinant == 0) {
            throw new RuntimeException("Cannot get inverse of singular matrix.");
        }

        int order = getRowsCount();
        IMatrix minors = newInstance(order, order);

        for(int i = 0; i < order; i++) {
            for(int j = 0; j < order; j++) {
                IMatrix sub = subMatrix(i, j, true);
                double value = ((i + j) % 2 == 1 ? -1 : 1) * sub.determinant() / determinant;
                minors.set(j, i, value);
            }
        }

        return minors;
    }

    @Override
    public IMatrix subMatrix(int row, int column, boolean liveView) {
        if(liveView) {
            return new MatrixSubMatrixView(this, row, column);
        } else {
            int rows = getRowsCount() - 1;
            int cols = getColsCount() - 1;

            IMatrix sub = newInstance(rows, cols);

            for(int i = 0, iIndex = 0; i <= rows; i++) {
                if(i == row) {
                    continue;
                }

                for(int j = 0, jIndex = 0; j <= cols; j++) {
                    if (j == column) {
                        continue;
                    }

                    double value = get(i, j);
                    sub.set(iIndex, jIndex, value);

                    jIndex++;
                }

                iIndex++;
            }

            return sub;
        }
    }

    @Override
    public double[][] toArray() {
        int rows = getRowsCount();
        int cols = getColsCount();

        double[][] arr = new double[rows][cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[i][j] = get(i, j);
            }
        }

        return arr;
    }

    @Override
    public String toString() {
        return toString(3);
    }

    @Override
    public String toString(int precision) {
        StringJoiner global = new StringJoiner("\n");

        String format = "%." + precision + "f";
        for(int i = 0, rows = getRowsCount(); i < rows; i++) {
            StringJoiner sj = new StringJoiner(", ", "[", "]");
            for(int j = 0, cols = getColsCount(); j < cols; j++) {
                String num = String.format(Locale.US, format, get(i, j));
                sj.add(num);
            }

            global.add(sj.toString());
        }

        return global.toString();
    }

    private static void elementWiseUnaryOperation(IMatrix matrix, Function<Double, Double> function) {
        for(int i = 0, rows = matrix.getRowsCount(); i < rows; i++) {
            for(int j = 0, cols = matrix.getColsCount(); j < cols; j++) {
                double value = function.apply(matrix.get(i, j));
                matrix.set(i, j, value);
            }
        }
    }

    private static void elementWiseBinaryOperation(
            IMatrix first, IMatrix second, BiFunction<Double, Double, Double> function) {
        checkDimensions(first, second);

        for(int i = 0, rows = first.getRowsCount(); i < rows; i++) {
            for(int j = 0, cols = first.getColsCount(); j < cols; j++) {
                double value = function.apply(first.get(i, j), second.get(i, j));
                first.set(i, j, value);
            }
        }
    }

    private static void checkDimensions(IMatrix first, IMatrix second) {
        if (first.getRowsCount() != second.getRowsCount() || first.getColsCount() != second.getColsCount()) {
            throw new RuntimeException("Two matrices do not have the same dimensions.");
        }
    }
}
