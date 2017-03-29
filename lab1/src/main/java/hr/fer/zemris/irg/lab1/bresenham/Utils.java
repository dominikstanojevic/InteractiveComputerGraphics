package hr.fer.zemris.irg.lab1.bresenham;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

import java.awt.Point;

/**
 * Created by Dominik on 17.3.2017..
 */
public class Utils {
    public static Point intersection(Line firstLine, Line secondLine) {
        double[][] m = new double[2][2];
        double[] c = new double[2];

        double[] firstParams = getParams(firstLine);
        m[0][0] = firstParams[0];
        m[0][1] = firstParams[1];
        c[0] = firstParams[2];

        double[] secondParams = getParams(secondLine);
        m[1][0] = secondParams[0];
        m[1][1] = secondParams[1];
        c[1] = secondParams[2];

        IMatrix matrix = new Matrix(m, false);
        IMatrix constants = new Vector(c).toColumnMatrix();

        try {
            IMatrix coordinates = matrix.nInvert().nMultiply(constants);
            Point point = new Point((int) Math.round(coordinates.get(0, 0)), (int) Math.round(coordinates.get(1, 0)));
            return point;
        } catch (Exception e) {
            return null;
        }
    }

    public static double[] getParams(Line line) {
        double A = line.end.y - line.start.y;
        double B = line.start.x - line.end.x;
        double C = A * line.start.x + B * line.start.y;
        return new double[]{A, B, C};
    }

    public static double length(Point first, Point second) {
        double dx = second.x - first.x;
        double dy = second.y - first.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static class Pair<T, U> {
        public T first;
        public U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }
}
