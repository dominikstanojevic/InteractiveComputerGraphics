package hr.fer.zemris.irg.lab1.bresenham;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.lab1.bresenham.listeners.BresenhamEventListener;
import hr.fer.zemris.irg.lab1.bresenham.listeners.BresenhamKeyListener;
import hr.fer.zemris.irg.lab1.bresenham.listeners.BresenhamMouseListener;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dominik on 17.3.2017..
 */
public class BresenhamFrame extends Frame{
    private static final long serialVersionUID = -157128680560391502L;

    BresenhamData data = new BresenhamData();

    public BresenhamFrame() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas glCanvas = new GLCanvas(glCapabilities);

        glCanvas.addGLEventListener(new BresenhamEventListener(glCanvas, data));
        glCanvas.addMouseListener(new BresenhamMouseListener(glCanvas, data));
        glCanvas.addKeyListener(new BresenhamKeyListener(glCanvas, data));

        setTitle("Bresenham");
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
        EventQueue.invokeLater(BresenhamFrame::new);
    }
}
