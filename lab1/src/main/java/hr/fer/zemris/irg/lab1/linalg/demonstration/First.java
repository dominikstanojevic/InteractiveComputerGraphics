package hr.fer.zemris.irg.lab1.linalg.demonstration;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

/**
 * Created by Dominik on 14.3.2017..
 */
public class First {
    public static void main(String[] args) {
        IVector v1 = Vector.parseSimple("2 3 -4").nAdd(Vector.parseSimple("-1 4 -3"));
        System.out.println(v1);
        printHorizontal();

        double s = v1.scalarProduct(Vector.parseSimple("-1 4 -3"));
        System.out.println(s);
        printHorizontal();

        IVector v2 = v1.nVectorProduct(Vector.parseSimple("2 2 4"));
        System.out.println(v2);
        printHorizontal();

        IVector v3 = v2.nNormalize();
        System.out.println(v3);
        printHorizontal();

        IVector v4 = v2.scalarMultiply(-1);
        System.out.println(v4);
        printHorizontal();

        IMatrix temp1 = Matrix.parseSimple("1 2 3 | 2 1 3 | 4 5 1");
        IMatrix temp2 = Matrix.parseSimple("-1 2 -3 | 5 -2 7 | -4 -1 3");

        IMatrix m1 = temp1.nAdd(temp2);
        System.out.println(m1);
        printHorizontal();

        IMatrix m2 = temp1.nMultiply(temp2.nTranspose(true));
        System.out.println(m2);
        printHorizontal();

        IMatrix m3 = Matrix.parseSimple("-24 18 5 | 20 -15 -4 | -5 4 1").nInvert()
                .nMultiply(Matrix.parseSimple("1 2 " + "3 | " + "0 1 " + "4 | " + "5 6 " + "0").nInvert());
        System.out.println(m3);
        printHorizontal();
    }

    public static void printHorizontal() {
        System.out.println("-------------------------");
    }
}
