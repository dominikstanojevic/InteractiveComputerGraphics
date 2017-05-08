package hr.fer.zemris.irg.lineremoval.first;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import hr.fer.zemris.irg.projections.AbstractProjectionListener;
import hr.fer.zemris.irg.projections.ProjectionFrame;
import hr.fer.zemris.irg.projections.Utils;
import hr.fer.zemris.irg.shapes3D.models.Face3D;
import hr.fer.zemris.irg.shapes3D.models.ObjectModel;
import hr.fer.zemris.irg.shapes3D.models.Vertex3D;

import java.awt.EventQueue;
import java.io.IOException;

/**
 * Created by Dominik on 7.5.2017..
 */
public class FirstRemovalListener extends AbstractProjectionListener {
    public FirstRemovalListener(ProjectionFrame.ProjectionData data) {
        super(data);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glPolygonMode(GL.GL_FRONT, GL2GL3.GL_LINE);
        gl2.glEnable(GL.GL_CULL_FACE);
        gl2.glCullFace(GL.GL_BACK);

        super.display(drawable);
    }

    @Override
    protected void drawObject(GL2 gl2) {
        for (Face3D face : data.model.getTriangles()) {
            gl2.glColor3d(255, 0, 0);
            gl2.glBegin(GL2.GL_POLYGON);
            for (int i = 0; i < 3; i++) {
                int index = face.getIndex(i);
                Vertex3D vertex = data.model.getVertex3DForIndex(index);
                gl2.glVertex3d(vertex.x, vertex.y, vertex.z);
            }
            gl2.glEnd();
        }
    }

    @Override
    public void setProjectionMatrix(GL2 gl2) {
        gl2.glFrustum(-0.5, 0.5, -0.5, 0.5, 1, 100);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RuntimeException("Invalid number of arguments.");
        }
        String path = args[0].trim();

        ObjectModel model = Utils.preprocessModel(path);
        ProjectionFrame.ProjectionData data = new ProjectionFrame.ProjectionData(model);
        EventQueue.invokeLater(() -> new ProjectionFrame(data, new FirstRemovalListener(data)));
    }
}
