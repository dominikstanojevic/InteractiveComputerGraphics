package hr.fer.zemris.irg.lineremoval.second;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.projections.ProjectionFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Dominik on 8.5.2017..
 */
public class RemovalKeyListener extends KeyAdapter {
    private GLCanvas canvas;
    private ProjectionFrame.ProjectionData data;

    public RemovalKeyListener(GLCanvas canvas, ProjectionFrame.ProjectionData data) {
        this.data = data;
        this.canvas = canvas;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_1) {
            data.method = RemovalMethod.WITHOUT_REMOVAL;
            canvas.repaint();
        } else if (code == KeyEvent.VK_2) {
            data.method = RemovalMethod.FIRST_METHOD;
            canvas.repaint();
        } else if (code == KeyEvent.VK_3) {
            data.method = RemovalMethod.SECOND_METHOD;
            canvas.repaint();
        } else if (code == KeyEvent.VK_4) {
            data.method = RemovalMethod.THIRD_METHOD;
            canvas.repaint();
        }
    }
}
