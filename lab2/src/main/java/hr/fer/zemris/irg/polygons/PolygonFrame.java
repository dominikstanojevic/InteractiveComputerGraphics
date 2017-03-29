package hr.fer.zemris.irg.polygons;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.polygons.listeners.PolygonGLListener;
import hr.fer.zemris.irg.polygons.listeners.PolygonKeyListener;
import hr.fer.zemris.irg.polygons.listeners.PolygonMouseListener;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dominik on 27.3.2017..
 */
public class PolygonFrame extends Frame {
    private static final long serialVersionUID = 1346902320985885043L;

    private PolygonData data = new PolygonData();

    public PolygonFrame() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas glCanvas = new GLCanvas(glCapabilities);

        glCanvas.addGLEventListener(new PolygonGLListener(glCanvas, data));

        PolygonMouseListener pml = new PolygonMouseListener(glCanvas, data);
        glCanvas.addMouseListener(pml);
        glCanvas.addMouseMotionListener(pml);

        glCanvas.addKeyListener(new PolygonKeyListener(glCanvas, data));


        setTitle("Polygon");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        add(glCanvas);
        setSize(800, 800);
        setVisible(true);

        glCanvas.requestFocusInWindow();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(PolygonFrame::new);
    }
}
