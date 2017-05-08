package hr.fer.zemris.irg.projections;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import hr.fer.zemris.irg.shapes3D.models.Face3D;
import hr.fer.zemris.irg.shapes3D.models.Vertex3D;

/**
 * Created by Dominik on 7.5.2017..
 */
public abstract class AbstractProjectionListener implements GLEventListener {
    protected ProjectionFrame.ProjectionData data;
    protected GLU glu;

    public AbstractProjectionListener(ProjectionFrame.ProjectionData data) {
        this.data = data;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        this.glu = new GLU();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClearColor(0, 255, 0, 1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl2.glLoadIdentity();

        glu.gluLookAt(data.eye.get(0), 4, data.eye.get(2), 0, 0, 0, 0, 1, 0);

        drawObject(gl2);

        gl2.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();
        setProjectionMatrix(gl2);
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

        gl2.glViewport(0, 0, width, height);
    }

    public abstract void setProjectionMatrix(GL2 gl2);

    protected void drawObject(GL2 gl2) {
        for (Face3D face : data.model.getTriangles()) {
            gl2.glColor3d(255, 0, 0);
            gl2.glBegin(GL.GL_LINE_LOOP);
            for (int i = 0; i < 3; i++) {
                int index = face.getIndex(i);
                Vertex3D vertex = data.model.getVertex3DForIndex(index);
                gl2.glVertex3d(vertex.x, vertex.y, vertex.z);
            }
            gl2.glEnd();
        }
    }
}
