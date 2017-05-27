package hr.fer.zemris.irg.fractals.mandelbrot.listeners;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import hr.fer.zemris.irg.fractals.mandelbrot.Complex;
import hr.fer.zemris.irg.fractals.mandelbrot.MandelbrotData;

/**
 * Created by Dominik on 27.5.2017..
 */
public class MandelbrotFractalGLListener implements GLEventListener {
    private MandelbrotData data;

    public MandelbrotFractalGLListener(MandelbrotData data) {
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

        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();

        gl2.glBegin(GL.GL_POINTS);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Complex c = data.getComplex(i, j, width, height);
                int n = divergenceTest(c, data.limit, data.order);
                double[] rgb = data.scheme.scheme(n, data.limit);

                gl2.glColor3d(rgb[0], rgb[1], rgb[2]);
                gl2.glVertex2i(i, j);
            }
        }
        gl2.glEnd();

        gl2.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl2.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluOrtho2D(0.0, width - 1, height - 1, 0);

        gl2.glViewport(0, 0, width, height);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();
    }

    private int divergenceTest(Complex c, int limit, int order) {
        Complex z = Complex.ZERO;
        for (int i = 1; i <= limit; i++) {
            z = z.power(order).add(c);
            double module = z.module();
            if (module * module > order) {
                return i;
            }
        }

        return -1;
    }
}
