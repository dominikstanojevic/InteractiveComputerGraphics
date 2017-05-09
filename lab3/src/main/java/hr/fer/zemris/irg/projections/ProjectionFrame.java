package hr.fer.zemris.irg.projections;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;
import hr.fer.zemris.irg.lineremoval.second.RemovalMethod;
import hr.fer.zemris.irg.shapes3D.models.ObjectModel;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dominik on 7.5.2017..
 */
public class ProjectionFrame extends Frame {
    private static final long serialVersionUID = 2602184501294126028L;

    private ProjectionData data;

    public ProjectionFrame(ProjectionData data, GLEventListener glListener) {
        this.data = data;

        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas glCanvas = new GLCanvas(glCapabilities);

        glCanvas.addGLEventListener(glListener);
        glCanvas.addKeyListener(new ProjectionKeyEventListener(glCanvas, data));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        add(glCanvas);
        setSize(640, 480);
        setVisible(true);

        glCanvas.requestFocusInWindow();
    }

    public static class ProjectionData {
        public static final double DEFAULT_ANGLE = 18.4349488;
        public static final double DEFAULT_INCREMENT = 1;
        public static final double DEFAULT_R = 3.16227766;

        public ObjectModel model;
        public double angle;
        public IVector eye;
        public RemovalMethod method = RemovalMethod.WITHOUT_REMOVAL;

        public ProjectionData(ObjectModel model) {
            this.model = model;

            this.angle = DEFAULT_ANGLE;
            this.eye = new Vector(new double[] { 3, 4, 1 });
        }

        public void incrementAngle() {
            angle += ProjectionData.DEFAULT_INCREMENT;
            updateEye();
        }

        public void decrementAngle() {
            angle -= DEFAULT_INCREMENT;
            updateEye();
        }

        public void resetAngle() {
            this.angle = DEFAULT_ANGLE;
            updateEye();
        }

        private void updateEye() {
            double rad = Math.toRadians(angle);
            double x = DEFAULT_R * Math.cos(rad);
            double z = DEFAULT_R * Math.sin(rad);
            eye.set(0, x);
            eye.set(2, z);
        }
    }
}
