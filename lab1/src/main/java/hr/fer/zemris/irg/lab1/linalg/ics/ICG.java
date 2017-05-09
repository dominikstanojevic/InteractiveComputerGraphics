package hr.fer.zemris.irg.lab1.linalg.ics;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

/**
 * Created by Dominik on 6.5.2017..
 */
public class ICG {
    public static IMatrix translate3D(double dx, double dy, double dz) {
        return null;
    }

    public static IMatrix scale3D(double sx, double sy, double sz) {
        return null;
    }

    public static IMatrix lookAtMatrix(IVector eye, IVector center, IVector viewUp) {
        IVector zNorm = eye.nSub(center).normalize();
        IVector xNorm = viewUp.nVectorProduct(zNorm).normalize();
        IVector yNorm = zNorm.nVectorProduct(xNorm).normalize();

        double[][] matrix = new double[4][4];
        for (int i = 0; i < 3; i++) {
            matrix[i][0] = xNorm.get(i);
            matrix[i][1] = yNorm.get(i);
            matrix[i][2] = zNorm.get(i);
        }

        matrix[3][0] = -xNorm.scalarProduct(eye);
        matrix[3][1] = -yNorm.scalarProduct(eye);
        matrix[3][2] = -zNorm.scalarProduct(eye);
        matrix[3][3] = 1;

        return new Matrix(matrix, false);
    }

    public static IMatrix buildFrustumMatrix(double l, double r, double b, double t, int n, int f) {
        double[][] mat = new double[4][4];
        double dw = r - l;
        double dh = t - b;
        double d = f - n;
        double n2 = 2 * n;

        mat[0][0] = n2 / dw;
        mat[2][0] = (r + l) / dw;
        mat[1][1] = n2 / dh;
        mat[2][1] = (t + b) / dh;
        mat[2][2] = -(f + n) / d;
        mat[3][2] = -(f * n2) / d;
        mat[2][3] = -1;

        return new Matrix(mat, false);
    }

    public static boolean isAntiClockWise(IVector firstPoint, IVector secondPoint, IVector thirdPoint) {
        if (thirdPoint.scalarProduct(getEdgeForPoints(firstPoint, secondPoint)) < 0) {
            return false;
        }
        if (firstPoint.scalarProduct(getEdgeForPoints(secondPoint, thirdPoint)) < 0) {
            return false;
        }
        if (secondPoint.scalarProduct(getEdgeForPoints(thirdPoint, firstPoint)) < 0) {
            return false;
        }

        return true;
    }

    public static IVector getEdgeForPoints(IVector start, IVector end) {
        double dy = start.get(1) - end.get(1);
        double dx = start.get(0) - end.get(0);

        double c = start.get(0) * end.get(1) - start.get(1) * end.get(0);

        return new Vector(new double[] { dy, -dx, c });
    }

    public static void main(String[] args) {
        IMatrix tp = ICG.lookAtMatrix(new Vector(new double[] { 3, 4, 1 }), new Vector(new double[] { 0, 0, 0 }),
                new Vector(new double[] { 0, 1, 0 }));
        IMatrix pr = buildFrustumMatrix(-0.5, 0.5, -0.5, 0.5, 1, 100);
        IMatrix m = tp.nMultiply(pr);

        IMatrix test = new Vector(new double[] { -1, -1, -1, 1 }).toRowMatrix(false);
        System.out.println(tp);
        System.out.println(pr);
        System.out.println(m);
        System.out.println(test.nMultiply(m).toVector(false).nFromHomogeneous());
    }
}
