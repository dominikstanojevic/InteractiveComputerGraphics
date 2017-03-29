package hr.fer.zemris.irg.lab1.first.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.lab1.first.ColorContainer;
import hr.fer.zemris.irg.lab1.first.Data;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Dominik on 15.3.2017..
 */
public class ExampleKeyAdapter extends KeyAdapter {
    Data data;
    GLCanvas canvas;

    public ExampleKeyAdapter(Data data, GLCanvas canvas) {
        this.data = data;
        this.canvas = canvas;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(data.currentTriangle == null) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_P) {
                data.colorIndex = Math.floorMod(data.colorIndex - 1, ColorContainer.getInstance().getColorCount());
            } else if(key == KeyEvent.VK_N) {
                data.colorIndex = (data.colorIndex + 1) % ColorContainer.getInstance().getColorCount();
            }

            canvas.repaint();
        }
    }
}
