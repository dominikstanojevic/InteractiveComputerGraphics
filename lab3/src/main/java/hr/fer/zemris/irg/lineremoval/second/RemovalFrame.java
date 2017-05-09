package hr.fer.zemris.irg.lineremoval.second;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.projections.ProjectionFrame;
import hr.fer.zemris.irg.projections.ProjectionKeyEventListener;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dominik on 8.5.2017..
 */
public class RemovalFrame extends Frame {
    private static final long serialVersionUID = -7671979642783112046L;

    private ProjectionFrame.ProjectionData data;

    public RemovalFrame(ProjectionFrame.ProjectionData data, GLEventListener glListener) {
        this.data = data;

        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas glCanvas = new GLCanvas(glCapabilities);

        glCanvas.addGLEventListener(glListener);

        RemovalKeyListener k1 = new RemovalKeyListener(glCanvas, data);
        ProjectionKeyEventListener k2 = new ProjectionKeyEventListener(glCanvas, data);
        glCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                k1.keyPressed(e);
                k2.keyPressed(e);
            }
        });

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
}

