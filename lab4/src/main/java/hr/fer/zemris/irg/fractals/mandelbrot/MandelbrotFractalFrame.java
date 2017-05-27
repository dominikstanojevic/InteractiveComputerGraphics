package hr.fer.zemris.irg.fractals.mandelbrot;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.fractals.mandelbrot.listeners.MandelbrotFractalGLListener;
import hr.fer.zemris.irg.fractals.mandelbrot.listeners.MandelbrotKeyListener;
import hr.fer.zemris.irg.fractals.mandelbrot.listeners.MandelbrotMouseListener;

import javax.swing.SwingUtilities;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dominik on 27.5.2017..
 */
public class MandelbrotFractalFrame extends Frame {
    private static final long serialVersionUID = -2112251882638589613L;

    public MandelbrotFractalFrame() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(glCapabilities);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        MandelbrotData data = new MandelbrotData();

        canvas.addGLEventListener(new MandelbrotFractalGLListener(data));
        canvas.addKeyListener(new MandelbrotKeyListener(canvas, data));
        canvas.addMouseListener(new MandelbrotMouseListener(canvas, data));

        add(canvas);
        setTitle("Fractal");
        setSize(640, 480);
        setVisible(true);

        canvas.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MandelbrotFractalFrame::new);
    }
}
