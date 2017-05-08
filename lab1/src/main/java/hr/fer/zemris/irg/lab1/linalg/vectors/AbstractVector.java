package hr.fer.zemris.irg.lab1.linalg.vectors;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.MatrixVectorView;

import java.util.Locale;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Dominik on 13.3.2017..
 */
public abstract class AbstractVector implements IVector {
    public IVector add(IVector other) {
        elementWiseBinaryOperation(this, other, (a, b) -> a + b);
        return this;
    }

    @Override
    public IVector nAdd(IVector other) {
        IVector copy = copy();
        return copy.add(other);
    }

    @Override
    public IVector sub(IVector other) {
        elementWiseBinaryOperation(this, other, (a, b) -> a - b);
        return this;
    }

    @Override
    public IVector nSub(IVector other) {
        IVector copy = copy();
        return copy.sub(other);
    }

    @Override
    public IVector scalarMultiply(double scalar) {
        elementWiseUnaryOperation(this, v -> v * scalar);
        return this;
    }

    @Override
    public IVector nScalarMultiply(double scalar) {
        IVector copy = copy();
        return copy.scalarMultiply(scalar);
    }

    @Override
    public double norm() {
        double total = 0;
        for (int i = 0, size = getDimension(); i < size; i++) {
            double value = get(i);
            total += value * value;
        }

        return Math.sqrt(total);
    }

    @Override
    public IVector normalize() {
        double norm = norm();
        elementWiseUnaryOperation(this, v -> v / norm);
        return this;
    }

    @Override
    public IVector nNormalize() {
        IVector copy = copy();
        return copy.normalize();
    }

    @Override
    public double cosine(IVector other) {
        double dot = this.scalarProduct(other);
        return dot / (this.norm() * other.norm());
    }

    @Override
    public double scalarProduct(IVector other) {
        checkDimensions(this, other);

        double dot = 0;
        for (int i = 0, size = getDimension(); i < size; i++) {
            dot += get(i) * other.get(i);
        }

        return dot;
    }

    @Override
    public IVector nVectorProduct(IVector other) {
        if (this.getDimension() != 3 || other.getDimension() != 3) {
            throw new RuntimeException("Given vectors are not three dimensional.");
        }

        IVector cross = newInstance(3);

        double i = get(1) * other.get(2) - get(2) * other.get(1);
        cross.set(0, i);

        double j = get(2) * other.get(0) - get(0) * other.get(2);
        cross.set(1, j);

        double k = get(0) * other.get(1) - get(1) * other.get(0);
        cross.set(2, k);

        return cross;
    }

    @Override
    public IVector nFromHomogeneous() {
        int size = getDimension() - 1;
        if (size < 1) {
            throw new RuntimeException("Cannot get vector from homogeneous.");
        }

        double h = get(size);
        IVector vector = newInstance(size);
        for (int i = 0; i < size; i++) {
            vector.set(i, this.get(i) / h);
        }
        return vector;
    }

    @Override
    public double[] toArray() {
        int size = getDimension();
        double[] array = new double[size];

        for (int i = 0; i < size; i++) {
            array[i] = get(i);
        }

        return array;
    }

    @Override
    public IVector copyPart(int start, int length) {
        checkLength(length);

        IVector vector = newInstance(length);
        for (int i = 0, size = getDimension(); i < length; i++) {
            int index = start + i;
            if (index >= size) {
                vector.set(i, 0);
            } else {
                vector.set(i, get(index));
            }
        }

        return vector;
    }

    protected void checkLength(int length) {
        if (length < 1) {
            throw new RuntimeException("Cannot create vector with length less than 1.");
        }
    }

    @Override
    public String toString() {
        return toString(3);
    }

    @Override
    public String toString(int precision) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        String format = "%." + precision + "f";
        for (int i = 0, size = getDimension(); i < size; i++) {
            String num = String.format(Locale.US, format, get(i));
            sj.add(num);
        }

        return sj.toString();
    }

    @Override
    public IMatrix toRowMatrix(boolean liveView) {
        if (liveView) {
            return new MatrixVectorView(this, true);
        } else {
            double[][] mat = new double[1][this.getDimension()];
            mat[0] = this.toArray();
            return new Matrix(mat, false);
        }
    }

    @Override
    public IMatrix toColumnMatrix(boolean liveView) {
        if (liveView) {
            return new MatrixVectorView(this, false);
        } else {
            int n = this.getDimension();
            double[][] mat = new double[n][1];
            for (int i = 0; i < n; i++) {
                mat[i][0] = this.get(i);
            }

            return new Matrix(mat, false);
        }
    }

    private static void checkDimensions(IVector first, IVector second) {
        if (first.getDimension() != second.getDimension()) {
            throw new RuntimeException("Vectors sizes are different.");
        }
    }

    private static void elementWiseUnaryOperation(IVector vector, Function<Double, Double> function) {
        for (int i = 0, size = vector.getDimension(); i < size; i++) {
            double value = function.apply(vector.get(i));
            vector.set(i, value);
        }
    }

    private static void elementWiseBinaryOperation(
            IVector first, IVector second, BiFunction<Double, Double, Double> function) {
        checkDimensions(first, second);

        for (int i = 0, size = first.getDimension(); i < size; i++) {
            double value = function.apply(first.get(i), second.get(i));
            first.set(i, value);
        }
    }
}
