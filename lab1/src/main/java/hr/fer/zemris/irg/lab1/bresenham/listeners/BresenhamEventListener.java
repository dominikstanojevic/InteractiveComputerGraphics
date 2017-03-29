package hr.fer.zemris.irg.lab1.bresenham.listeners;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import hr.fer.zemris.irg.lab1.bresenham.BresenhamData;
import hr.fer.zemris.irg.lab1.bresenham.Line;

/**
 * Created by Dominik on 17.3.2017..
 */
public class BresenhamEventListener implements com.jogamp.opengl.GLEventListener {
    private GLCanvas canvas;
    private BresenhamData data;

    public BresenhamEventListener(GLCanvas canvas, BresenhamData data) {
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
        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();

        //white background
        gl2.glClearColor(255, 255, 255, 1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl2.glLoadIdentity();
        gl2.glPointSize(1);

        if (data.cut) {
            drawBounds(gl2, width, height);

            int minWidth = width / 4;
            int maxWidth = width - minWidth;
            int minHeight = height / 4;
            int maxHeight = height - minHeight;

            for(Line line : data.lines) {
                line.drawCut(gl2, minWidth, maxWidth, minHeight, maxHeight);
            }
        } else {
            for(Line line : data.lines) {
                line.drawLineBresenham(gl2);
            }
        }

        if (data.control) {
            for(Line line : data.lines) {
                line.drawControl(gl2);
            }
        }

        gl2.glFlush();
    }

    private void drawBounds(GL2 gl2, int width, int height) {
        int minWidth = width / 4;
        int maxWidth = width - minWidth;
        int minHeight = height / 4;
        int maxHeight = height - minHeight;

        gl2.glColor3d(0, 255, 0);
        gl2.glBegin(GL2.GL_LINE_LOOP);
        gl2.glVertex2i(minWidth, minHeight);
        gl2.glVertex2i(minWidth, maxHeight);
        gl2.glVertex2i(maxWidth, maxHeight);
        gl2.glVertex2i(maxWidth, minHeight);
        gl2.glEnd();
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
