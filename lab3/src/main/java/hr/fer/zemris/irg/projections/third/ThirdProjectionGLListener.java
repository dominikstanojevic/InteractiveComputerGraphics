package hr.fer.zemris.irg.projections.third;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import hr.fer.zemris.irg.lab1.linalg.ics.ICG;
import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;
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
public class ThirdProjectionGLListener extends AbstractProjectionListener {
    private IMatrix pm;

    public ThirdProjectionGLListener(ProjectionFrame.ProjectionData data) {
        super(data);

        pm = ICG.buildFrustumMatrix(-0.5, 0.5, -0.5, 0.5, 1, 100);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClearColor(0, 255, 0, 1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl2.glLoadIdentity();

        IMatrix tp =
                ICG.lookAtMatrix(data.eye, new Vector(new double[] { 0, 0, 0 }), new Vector(new double[] { 0, 1, 0 }));
        IMatrix m = tp.nMultiply(pm);

        for (Face3D face : data.model.getTriangles()) {
            gl2.glColor3d(255, 0, 0);
            gl2.glBegin(GL.GL_LINE_LOOP);
            for (int i = 0; i < 3; i++) {
                int index = face.getIndex(i);
                Vertex3D vertex = data.model.getVertex3DForIndex(index);
                IVector transformed = getCoordinatesForVertex(vertex, m);
                gl2.glVertex3d(transformed.get(0), transformed.get(1), transformed.get(2));
            }
            gl2.glEnd();
        }

        gl2.glFlush();
    }

    private IVector getCoordinatesForVertex(Vertex3D vertex, IMatrix transformation) {
        IMatrix vector = new Vector(new double[] { vertex.x, vertex.y, vertex.z, 1}).toRowMatrix(true);
        return vector.nMultiply(transformation).toVector(true).nFromHomogeneous();
    }

    @Override
    public void setProjectionMatrix(GL2 gl2) {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RuntimeException("Invalid number of arguments.");
        }
        String path = args[0].trim();

        ObjectModel model = Utils.preprocessModel(path);
        ProjectionFrame.ProjectionData data = new ProjectionFrame.ProjectionData(model);
        EventQueue.invokeLater(() -> new ProjectionFrame(data, new ThirdProjectionGLListener(data)));
    }
}
