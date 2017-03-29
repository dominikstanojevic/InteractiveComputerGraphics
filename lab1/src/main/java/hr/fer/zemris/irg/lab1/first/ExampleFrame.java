package hr.fer.zemris.irg.lab1.first;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.lab1.first.listeners.ExampleGLListener;
import hr.fer.zemris.irg.lab1.first.listeners.ExampleKeyAdapter;
import hr.fer.zemris.irg.lab1.first.listeners.ExampleMouseListener;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dominik on 15.3.2017..
 */
public class ExampleFrame extends Frame {
    private static final long serialVersionUID = -1955052671781577562L;

    Data data = new Data();

    public ExampleFrame() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas glCanvas = new GLCanvas(glCapabilities);

        glCanvas.addGLEventListener(new ExampleGLListener(data, glCanvas));

        MouseAdapter mouseAdapter = new ExampleMouseListener(data, glCanvas);
        glCanvas.addMouseListener(mouseAdapter);
        glCanvas.addMouseMotionListener(mouseAdapter);

        glCanvas.addKeyListener(new ExampleKeyAdapter(data, glCanvas));

        setTitle("First example");
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
        EventQueue.invokeLater(ExampleFrame::new);
    }
}
