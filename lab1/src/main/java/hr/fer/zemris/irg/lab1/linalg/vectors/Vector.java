package hr.fer.zemris.irg.lab1.linalg.vectors;

import java.util.Arrays;

/**
 * Created by Dominik on 13.3.2017..
 */
public class Vector extends AbstractVector {
    private double[] elements;
    private boolean readOnly;

    public Vector(int length) {
        checkLength(length);

        this.elements = new double[length];
        this.readOnly = false;
    }

    public Vector(double[] elements) {
        this(false, true, elements);
    }

    public Vector(boolean readOnly, boolean copy, double[] elements) {
        checkLength(elements.length);

        if (copy) {
            this.elements = Arrays.copyOf(elements, elements.length);
        } else {
            this.elements = elements;
        }

        this.readOnly = readOnly;
    }

    @Override
    public double get(int index) {
        checkRange(index);

        return elements[index];
    }

    @Override
    public IVector set(int index, double value) {
        if(readOnly) {
            throw new RuntimeException("Cannot set value for read-only vector.");
        }
        checkRange(index);

        elements[index] = value;
        return this;
    }

    @Override
    public int getDimension() {
        return elements.length;
    }

    @Override
    public IVector copy() {
        return new Vector(readOnly, true, elements);
    }

    @Override
    public IVector newInstance(int length) {
        return new Vector(length);
    }

    public static Vector parseSimple(String vector) {
        String[] elements = vector.split("\\s+");
        double[] array = new double[elements.length];

        for(int i = 0; i < array.length; i++) {
            array[i] = Double.parseDouble(elements[i]);
        }

        return new Vector(false, false, array);
    }

    private void checkRange(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
    }
}
