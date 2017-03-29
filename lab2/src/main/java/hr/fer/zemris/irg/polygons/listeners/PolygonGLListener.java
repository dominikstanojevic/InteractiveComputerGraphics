package hr.fer.zemris.irg.polygons.listeners;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import hr.fer.zemris.irg.polygons.PolygonData;
import hr.fer.zemris.irg.polygons.models.Polygon;

/**
 * Created by Dominik on 27.3.2017..
 */
public class PolygonGLListener implements GLEventListener {
    private GLCanvas canvas;
    private PolygonData data;

    public PolygonGLListener(GLCanvas canvas, PolygonData data) {
        this.canvas = canvas;
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

        if (data.convexity) {
            gl2.glClearColor(0, 255, 0, 1);
        } else {
            gl2.glClearColor(255, 255, 255, 1);
        }
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl2.glLoadIdentity();
        gl2.glPointSize(1);

        if (data.fill && data.polygon.convexityTest().getValue() == Polygon.Orientation.CLOCKWISE) {
            data.polygon.drawFill(gl2);
        } else {
            data.polygon.draw(gl2);
        }

        gl2.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D(0.0, width, 0, height);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();

        gl2.glViewport(0, 0, width, height);
    }
}
