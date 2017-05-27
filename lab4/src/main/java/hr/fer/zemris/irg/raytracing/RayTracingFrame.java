package hr.fer.zemris.irg.raytracing;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.raytracing.models.RTScene;

import javax.swing.SwingUtilities;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Dominik on 25.5.2017..
 */
public class RayTracingFrame extends Frame {
    private static final long serialVersionUID = 6237090363361008918L;

    public RayTracingFrame(RTScene scene) {
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

        canvas.addGLEventListener(new RayTracingGLListener(scene));

        add(canvas);
        setTitle("Ray Tracing");
        setSize(640, 480);
        setVisible(true);

        canvas.requestFocusInWindow();
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            throw new RuntimeException("Invalid number of arguments.");
        }

        Path path = Paths.get(args[0].trim());
        RTScene scene = RTScene.loadScene(path);

        SwingUtilities.invokeLater(() -> new RayTracingFrame(scene));
    }
}
