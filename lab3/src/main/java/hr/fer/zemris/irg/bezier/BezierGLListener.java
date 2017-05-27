package hr.fer.zemris.irg.bezier;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 8.5.2017..
 */
public class BezierGLListener implements GLEventListener {
    private BezierData data;

    public BezierGLListener(BezierData data) {
        this.data = data;
    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClearColor(0, 255, 0, 1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

        if (data.points.size() > 1) {
            drawPolygon(gl2);
            drawBezier(gl2, data.points);

            List<Point> points = getControlPoints(data.points);
            drawBezier(gl2, points);
        }

        gl2.glFlush();
    }

    private List<Point> getControlPoints(List<Point> interpolated) {
        List<Point> points = new ArrayList<>();
        int n = interpolated.size() - 1;
        int[] factors = factors(n);

        //punimo matricu T
        IMatrix T = new Matrix(n + 1, n + 1);
        for (int i = 0; i <= n; i++) {
            double t = ((double) i) / n;
            for (int j = 0; j <= n; j++) {
                if (j == 0) {
                    T.set(i, j, factors[j] * Math.pow(1 - t, n));
                } else if (j == n) {
                    T.set(i, j, factors[j] * Math.pow(t, n));
                } else {
                    T.set(i, j, factors[j] * Math.pow(t, j) * Math.pow(1 - t, n - j));
                }
            }
        }

        //punimo matricu P
        IMatrix P = new Matrix(n + 1, 2);
        for (int i = 0; i <= n; i++) {
            Point p = interpolated.get(i);
            P.set(i, 0, p.x);
            P.set(i, 1, p.y);
        }

        IMatrix R = T.nInvert().nMultiply(P);
        for (int i = 0; i <= n; i++) {
            Point p = new Point((int) Math.round(R.get(i, 0)), (int) Math.round(R.get(i, 1)));
            points.add(p);
        }

        return points;
    }

    private int[] factors(int n) {
        int[] factors = new int[n + 1];
        int a = 1;

        for (int i = 1; i <= n + 1; i++) {
            factors[i - 1] = a;
            a = a * (n - i + 1) / i;
        }

        return factors;
    }

    private void drawBezier(GL2 gl2, List<Point> points) {
        int n = points.size() - 1;
        int[] factors = factors(n);

        gl2.glColor3d(0, 0, 255);
        gl2.glBegin(GL.GL_LINE_STRIP);
        for (int i = 0; i <= BezierData.DIV; i++) {
            double t = ((double) i) / BezierData.DIV;
            Point2D.Double p = new Point2D.Double();
            for (int j = 0; j <= n; j++) {
                double b;
                if (j == 0) {
                    b = factors[j] * Math.pow(1 - t, n);
                } else if (j == n) {
                    b = factors[j] * Math.pow(t, n);
                } else {
                    b = factors[j] * Math.pow(t, j) * Math.pow(1 - t, n - j);
                }

                p.x += b * points.get(j).x;
                p.y += b * points.get(j).y;
            }

            gl2.glVertex2d(p.x, p.y);
        }
        gl2.glEnd();
    }

    public void drawPolygon(GL2 gl2) {
        gl2.glColor3d(255, 0, 0);
        gl2.glBegin(GL.GL_LINE_STRIP);
        for (Point p : data.points) {
            gl2.glVertex2i(p.x, p.y);
        }
        gl2.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D(0, width, height, 0);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();

        gl2.glViewport(0, 0, width, height);
    }
}
