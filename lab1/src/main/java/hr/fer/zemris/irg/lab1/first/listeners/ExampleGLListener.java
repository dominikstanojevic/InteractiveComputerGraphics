package hr.fer.zemris.irg.lab1.first.listeners;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import hr.fer.zemris.irg.lab1.first.ColorContainer;
import hr.fer.zemris.irg.lab1.first.Data;
import hr.fer.zemris.irg.lab1.first.Triangle;

import java.awt.Color;

/**
 * Created by Dominik on 15.3.2017..
 */
public class ExampleGLListener implements GLEventListener {
    public static double COLOR_EDGE_LENGTH = 20;

    private GLCanvas canvas;
    public Data data;

    public ExampleGLListener(Data data, GLCanvas canvas) {
        this.data = data;
        this.canvas = canvas;
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

        colorSquare(gl2, width, height);
        for (Triangle triangle : data.triangles) {
            triangle.display(gl2);
        }

        //add method for one and two points
       if(data.nPoints > 0) {
           data.currentTriangle.display(gl2);
       }

        gl2.glFlush();
    }

    private void colorSquare(GL2 gl2, int width, int height) {
        Color color = ColorContainer.getInstance().getColor(data.colorIndex);

        gl2.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
        gl2.glBegin(GL2.GL_QUADS);
        gl2.glVertex2d(width - COLOR_EDGE_LENGTH, 0);
        gl2.glVertex2d(width - COLOR_EDGE_LENGTH, COLOR_EDGE_LENGTH);
        gl2.glVertex2d(width, COLOR_EDGE_LENGTH);
        gl2.glVertex2d(width, 0);
        gl2.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D(0.0, width, height, 0);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();

        gl2.glViewport(0, 0, width, height);
    }
}
