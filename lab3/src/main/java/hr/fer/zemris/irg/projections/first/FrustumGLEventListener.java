package hr.fer.zemris.irg.projections.first;

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
public class FrustumGLEventListener extends AbstractProjectionListener {
    public FrustumGLEventListener(ProjectionFrame.ProjectionData data) {
        super(data);
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
        EventQueue.invokeLater(() -> new ProjectionFrame(data, new FrustumGLEventListener(data)));
    }
}
