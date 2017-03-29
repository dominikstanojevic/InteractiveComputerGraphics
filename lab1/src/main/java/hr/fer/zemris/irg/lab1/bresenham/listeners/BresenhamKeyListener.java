package hr.fer.zemris.irg.lab1.bresenham.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.lab1.bresenham.BresenhamData;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Dominik on 17.3.2017..
 */
public class BresenhamKeyListener extends KeyAdapter {
    private GLCanvas canvas;
    private BresenhamData data;

    public BresenhamKeyListener(GLCanvas canvas, BresenhamData data) {
        this.canvas = canvas;
        this.data = data;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_O) {
            data.cut = !data.cut;
        } else if(code == KeyEvent.VK_K) {
            data.control = !data.control;
        } else if(code == KeyEvent.VK_C) {
            data.lines.clear();
            data.temp = null;
        }

        canvas.repaint();
    }
}
