package hr.fer.zemris.irg.projections;

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Dominik on 7.5.2017..
 */
public class ProjectionKeyEventListener extends KeyAdapter {
    private GLCanvas canvas;
    private ProjectionFrame.ProjectionData data;

    public ProjectionKeyEventListener(GLCanvas canvas, ProjectionFrame.ProjectionData data) {
        this.data = data;
        this.canvas = canvas;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_R) {
            data.incrementAngle();
            canvas.repaint();
        } else if (code == KeyEvent.VK_L) {
            data.decrementAngle();
            canvas.repaint();
        } else if (code == KeyEvent.VK_ESCAPE) {
            data.resetAngle();
            canvas.repaint();
        }
    }
}
