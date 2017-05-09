package hr.fer.zemris.irg.bezier;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.bezier.listeners.BezierMouseListener;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dominik on 8.5.2017..
 */
public class BezierFrame extends Frame {
    private static final long serialVersionUID = 1773709087836549593L;

    public BezierFrame() {
        initGUI();
    }

    private void initGUI() {
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

        BezierData data = new BezierData();

        canvas.addGLEventListener(new BezierGLListener(data));

        MouseAdapter ml = new BezierMouseListener(data, canvas);
        canvas.addMouseListener(ml);
        canvas.addMouseMotionListener(ml);

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ESCAPE) {
                    data.points.clear();
                    canvas.repaint();
                }
            }
        });

        add(canvas);
        setTitle("Bezier Curve");
        setSize(640, 480);
        setVisible(true);

        canvas.requestFocusInWindow();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(BezierFrame::new);
    }
}
