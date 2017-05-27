package hr.fer.zemris.irg.fractals.mandelbrot.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.fractals.mandelbrot.MandelbrotData;
import hr.fer.zemris.irg.fractals.mandelbrot.sheme.ColorScheme;
import hr.fer.zemris.irg.fractals.mandelbrot.sheme.GrayScheme;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Dominik on 27.5.2017..
 */
public class MandelbrotKeyListener extends KeyAdapter {
    private MandelbrotData data;
    private GLCanvas canvas;

    public MandelbrotKeyListener(GLCanvas canvas, MandelbrotData data) {
        this.canvas = canvas;
        this.data = data;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_1 && data.order != 2) {
            data.order = 2;
            canvas.repaint();
        } else if (code == KeyEvent.VK_2 && data.order != 3) {
            data.order = 3;
            canvas.repaint();
        } else if (code == KeyEvent.VK_B && !(data.scheme instanceof GrayScheme)) {
            data.scheme = GrayScheme.getInstance();
            canvas.repaint();
        } else if (code == KeyEvent.VK_C && !(data.scheme instanceof ColorScheme)) {
            data.scheme = ColorScheme.getInstance();
            canvas.repaint();
        } else if (code == KeyEvent.VK_X && data.restoreCoordinates()) {
            canvas.repaint();
        }
    }
}
