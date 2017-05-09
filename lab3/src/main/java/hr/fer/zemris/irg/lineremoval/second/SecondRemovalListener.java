package hr.fer.zemris.irg.lineremoval.second;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import hr.fer.zemris.irg.lab1.linalg.ics.ICG;
import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;
import hr.fer.zemris.irg.projections.ProjectionFrame;
import hr.fer.zemris.irg.projections.Utils;
import hr.fer.zemris.irg.projections.third.ThirdProjectionGLListener;
import hr.fer.zemris.irg.shapes3D.models.Face3D;
import hr.fer.zemris.irg.shapes3D.models.ObjectModel;

import java.awt.EventQueue;
import java.io.IOException;

/**
 * Created by Dominik on 7.5.2017..
 */
public class SecondRemovalListener extends ThirdProjectionGLListener {
    public SecondRemovalListener(ProjectionFrame.ProjectionData data) {
        super(data);
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

        data.method.execute(data.model, data.eye);
        for (Face3D face : data.model.getTriangles()) {
            if (!data.model.isVisible(face)) {
                continue;
            }

            IVector first = getCoordinatesForVertex(data.model.getVertex3DForIndex(face.getIndex(0)), m);
            IVector second = getCoordinatesForVertex(data.model.getVertex3DForIndex(face.getIndex(1)), m);
            IVector third = getCoordinatesForVertex(data.model.getVertex3DForIndex(face.getIndex(2)), m);

            if (!data.method.projection(first, second, third)) {
                continue;
            }

            gl2.glColor3d(255, 0, 0);
            gl2.glBegin(GL.GL_LINE_LOOP);
            gl2.glVertex3d(first.get(0), first.get(1), first.get(2));
            gl2.glVertex3d(second.get(0), second.get(1), second.get(2));
            gl2.glVertex3d(third.get(0), third.get(1), third.get(2));
            gl2.glEnd();
        }

        gl2.glFlush();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RuntimeException("Invalid number of arguments.");
        }
        String path = args[0].trim();

        ObjectModel model = Utils.preprocessModel(path);
        ProjectionFrame.ProjectionData data = new ProjectionFrame.ProjectionData(model);
        EventQueue.invokeLater(() -> new RemovalFrame(data, new SecondRemovalListener(data)));
    }
}
