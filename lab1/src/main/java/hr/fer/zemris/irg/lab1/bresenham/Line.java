package hr.fer.zemris.irg.lab1.bresenham;

import com.jogamp.opengl.GL2;
import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

import java.awt.Point;
import java.util.BitSet;

/**
 * Created by Dominik on 16.3.2017..
 */
public class Line {
    public Point start;
    public Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public void drawControl(GL2 gl2) {
        Utils.Pair<Point, Point> controlPoints = getControlPoints();
        drawControl(gl2, controlPoints.first, controlPoints.second);
    }

    public void drawCut(GL2 gl2, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        Line segment = getSegmentInside(minWidth, maxWidth, minHeight, maxHeight);
        if (segment != null) {
            segment.drawLineBresenham(gl2);
        }
    }

    public void drawLineBresenham(GL2 gl2) {
        gl2.glColor3d(0, 0, 0);
        gl2.glBegin(GL2.GL_POINTS);
        if (start.x <= end.x) {
            if (start.y <= end.y) {
                bresenhamPositive(gl2, start.x, start.y, end.x, end.y);
            } else {
                bresenhamNegative(gl2, start.x, start.y, end.x, end.y);
            }
        } else {
            if (start.y >= end.y) {
                bresenhamPositive(gl2, end.x, end.y, start.x, start.y);
            } else {
                bresenhamNegative(gl2, end.x, end.y, start.x, start.y);
            }
        }
        gl2.glEnd();
    }

    private Utils.Pair<Point, Point> getControlPoints() {
        IMatrix matrix = getTransformationMatrix();

        IMatrix resStart = new Vector(new double[] { 4, 0, 1 }).toRowMatrix().nMultiply(matrix);
        Point cS = new Point((int) Math.round(resStart.get(0, 0)), (int) Math.round(resStart.get(0, 1)));

        double length = Utils.length(start, end);
        IMatrix resEnd = new Vector(new double[] { 4, length, 1 }).toRowMatrix().nMultiply(matrix);
        Point cE = new Point((int) Math.round(resEnd.get(0, 0)), (int) Math.round(resEnd.get(0, 1)));

        return new Utils.Pair<>(cS, cE);
    }

    private IMatrix getTransformationMatrix() {
        int dY = end.y - start.y;
        int dX = end.x - start.x;
        double angle = Math.atan2(dY, dX) - Math.PI / 2;

        double[][] rotation = new double[3][3];
        rotation[0][0] = rotation[1][1] = Math.cos(angle);
        double sin = Math.sin(angle);
        rotation[0][1] = sin;
        rotation[1][0] = -sin;
        rotation[2][2] = 1;
        IMatrix rotMatrix = new Matrix(rotation, false);

        double[][] translation = new double[3][3];
        translation[0][0] = translation[1][1] = translation[2][2] = 1;
        translation[2][0] = start.x;
        translation[2][1] = start.y;
        IMatrix transMatrix = new Matrix(translation, false);

        return rotMatrix.nMultiply(transMatrix);
    }

    private void drawControl(GL2 gl2, Point first, Point second) {
        gl2.glColor3d(255, 0, 0);
        gl2.glBegin(GL2.GL_LINE_STRIP);
        gl2.glVertex2i(first.x, first.y);
        gl2.glVertex2i(second.x, second.y);
        gl2.glEnd();
    }

    private void bresenhamNegative(GL2 gl2, int xs, int ys, int xe, int ye) {
        int x, yc, cor;
        int a, yf;

        if (-(ye - ys) <= xe - xs) {
            a = 2 * (ye - ys);
            yc = ys;
            yf = (xe - xs);
            cor = 2 * (xe - xs);
            for (x = xs; x <= xe; x++) {
                turnOn(gl2, x, yc);
                yf += a;
                if (yf <= 0) {
                    yf += cor;
                    yc -= 1;
                }
            }
        } else {
            x = xe;
            xe = ys;
            ys = x;
            x = xs;
            xs = ye;
            ye = x;
            a = 2 * (ye - ys);
            yc = ys;
            yf = (xe - xs);
            cor = 2 * (xe - xs);
            for (x = xs; x <= xe; x++) {
                turnOn(gl2, yc, x);
                yf += a;
                if (yf <= 0) {
                    yf += cor;
                    yc -= 1;
                }
            }
        }
    }

    private void bresenhamPositive(GL2 gl2, int xs, int ys, int xe, int ye) {
        int x, yc, cor;
        int a, yf;

        if (ye - ys <= xe - xs) {
            a = 2 * (ye - ys);
            yc = ys;
            yf = -(xe - xs);
            cor = -2 * (xe - xs);
            for (x = xs; x <= xe; x++) {
                turnOn(gl2, x, yc);
                yf += a;
                if (yf >= 0) {
                    yf += cor;
                    yc += 1;
                }
            }
        } else {
            x = xe;
            xe = ye;
            ye = x;
            x = xs;
            xs = ys;
            ys = x;
            a = 2 * (ye - ys);
            yc = ys;
            yf = -(xe - xs);
            cor = -2 * (xe - xs);
            for (x = xs; x <= xe; x++) {
                turnOn(gl2, yc, x);
                yf += a;
                if (yf >= 0) {
                    yf += cor;
                    yc += 1;
                }
            }
        }
    }

    private void turnOn(GL2 gl2, int x, int y) {
        gl2.glVertex2i(x, y);
    }

    private Line getSegmentInside(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        BitSet first = getCode(start, minWidth, maxWidth, minHeight, maxHeight);
        BitSet second = getCode(end, minWidth, maxWidth, minHeight, maxHeight);

        if (first.isEmpty() && second.isEmpty()) {
            return this;
        }

        if (first.intersects(second)) {
            return null;
        }

        Line left = new Line(new Point(minWidth, minHeight), new Point(minWidth, maxHeight));
        Line top = new Line(new Point(minWidth, maxHeight), new Point(maxWidth, maxHeight));
        Line right = new Line(new Point(maxWidth, maxHeight), new Point(maxWidth, minHeight));
        Line down = new Line(new Point(maxWidth, minHeight), new Point(minWidth, minHeight));

        Point newStart = getNewPoint(start, first, end, left, top, right, down);
        Point newEnd = getNewPoint(end, second, newStart, left, top, right, down);
        return new Line(newStart, newEnd);
    }

    private Point getNewPoint(
            Point point, BitSet code, Point other, Line left, Line top, Line right, Line down) {
        if (code.get(0)) {
            point = Utils.intersection(new Line(point, other), top);
            if (getCode(point, left.start.x, top.end.x, right.end.y, top.start.y).isEmpty()) {
                return point;
            }
        } if (code.get(1)) {
            point = Utils.intersection(new Line(point, other), down);
            if (getCode(point, left.start.x, top.end.x, right.end.y, top.start.y).isEmpty()) {
                return point;
            }
        }
        if (code.get(2)) {
            point = Utils.intersection(new Line(point, other), right);
            if (getCode(point, left.start.x, top.end.x, right.end.y, top.start.y).isEmpty()) {
                return point;
            }
        }
        if (code.get(3)) {
            point = Utils.intersection(new Line(point, other), left);
            if (getCode(point, left.start.x, top.end.x, right.end.y, top.start.y).isEmpty()) {
                return point;
            }
        }

        return point;
    }

    private BitSet getCode(Point point, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        BitSet code = new BitSet(4);
        if (point.y > maxHeight) {
            code.set(0);
        }
        if (point.y < minHeight) {
            code.set(1);
        }
        if (point.x > maxWidth) {
            code.set(2);
        }
        if (point.x < minWidth) {
            code.set(3);
        }

        return code;
    }
}
