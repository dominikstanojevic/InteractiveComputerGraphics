package hr.fer.zemris.irg.fractals.ifs;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.SwingUtilities;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Dominik on 27.5.2017..
 */
public class IFSFrame extends Frame {
    private static final long serialVersionUID = -5762410879990760093L;

    private IFSData data;

    public IFSFrame(IFSData data) {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(capabilities);

        canvas.addGLEventListener(new IFSGLListener(data));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        add(canvas);
        setSize(600, 600);
        setVisible(true);
        setTitle("IFS Fractal Viewer");

        canvas.requestFocusInWindow();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid number of arguments.");
            System.exit(-1);
        }

        Path path = Paths.get(args[0].trim());
        IFSData data = IFSData.parse(path);

        SwingUtilities.invokeLater(() -> new IFSFrame(data));
    }
}
