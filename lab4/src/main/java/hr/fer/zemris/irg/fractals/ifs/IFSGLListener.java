package hr.fer.zemris.irg.fractals.ifs;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

import java.util.Map;
import java.util.Random;

/**
 * Created by Dominik on 27.5.2017..
 */
public class IFSGLListener implements GLEventListener {
    private IFSData data;

    private Random random;

    public IFSGLListener(IFSData data) {
        this.data = data;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        random = new Random();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClearColor(1, 1, 1, 1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl2.glColor3d(0, 0.7, 0.3);

        gl2.glBegin(GL.GL_POINTS);
        for (int counter = 0; counter < data.pointsNumber; counter++) {
            double x0 = 0, y0 = 0;
            for (int i = 0; i < data.limit; i++) {
                double x, y;
                IFSData.Transformation trans = getRandomTransformation();

                x = trans.xTransform[0] * x0 + trans.xTransform[1] * y0 + trans.xTransform[2];
                y = trans.yTransform[0] * x0 + trans.yTransform[1] * y0 + trans.yTransform[2];

                x0 = x;
                y0 = y;
            }

            int drawX = (int) Math.round(data.eta[0] * x0 + data.eta[1]);
            int drawY = (int) Math.round(data.eta[2] * y0 + data.eta[3]);
            gl2.glVertex2i(drawX, drawY);
        }
        gl2.glEnd();

        gl2.glFlush();
    }

    private IFSData.Transformation getRandomTransformation() {
        double p = random.nextDouble();
        double total = 0;
        for (Map.Entry<IFSData.Transformation, Double> entry : data.transformations.entrySet()) {
            total += entry.getValue();
            if (total >= p) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Invalid probability.");
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl2.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluOrtho2D(0.0, width - 1, 0, height - 1);

        gl2.glViewport(0, 0, width, height);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();
    }
}
