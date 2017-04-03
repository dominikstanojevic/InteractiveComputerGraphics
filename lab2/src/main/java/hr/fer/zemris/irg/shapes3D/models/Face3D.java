package hr.fer.zemris.irg.shapes3D.models;

import java.util.Arrays;

/**
 * Created by Dominik on 31.3.2017..
 */
public class Face3D {
    private int[] indexes;

    public Face3D(int... indexes) {
        checkArray(indexes);

        this.indexes = indexes;
    }

    public Face3D(Face3D face3D) {
        this.indexes = Arrays.copyOf(face3D.indexes, face3D.indexes.length);
    }

    public int getIndex(int position) {
        if (position < 0 || position > 2) {
            throw new IndexOutOfBoundsException("Valid positions are between 0 and 2.");
        }

        return indexes[position];
    }

    private void checkArray(int[] indexes) {
        if (indexes == null || indexes.length != 3) {
            throw new RuntimeException("Invalid number of points.");
        }
    }
}
