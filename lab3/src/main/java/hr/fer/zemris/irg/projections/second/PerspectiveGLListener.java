package hr.fer.zemris.irg.projections.second;

import com.jogamp.opengl.GL2;
import hr.fer.zemris.irg.projections.AbstractProjectionListener;
import hr.fer.zemris.irg.projections.ProjectionFrame;
import hr.fer.zemris.irg.projections.Utils;
import hr.fer.zemris.irg.shapes3D.models.ObjectModel;

import java.awt.EventQueue;
import java.io.IOException;

/**
 * Created by Dominik on 7.5.2017..
 */
public class PerspectiveGLListener extends AbstractProjectionListener {

    public PerspectiveGLListener(ProjectionFrame.ProjectionData data) {
        super(data);
    }

    @Override
    public void setProjectionMatrix(GL2 gl2) {
        glu.gluPerspective(53.13, 1, 1, 100);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RuntimeException("Invalid number of arguments.");
        }
        String path = args[0].trim();

        ObjectModel model = Utils.preprocessModel(path);
        ProjectionFrame.ProjectionData data = new ProjectionFrame.ProjectionData(model);
        EventQueue.invokeLater(() -> new ProjectionFrame(data, new PerspectiveGLListener(data)));
    }
}
