package hr.fer.zemris.irg.polygons.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.polygons.PolygonData;
import hr.fer.zemris.irg.polygons.models.Polygon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Dominik on 27.3.2017..
 */
public class PolygonKeyListener extends KeyAdapter {
    private GLCanvas canvas;
    private PolygonData data;

    public PolygonKeyListener(GLCanvas canvas, PolygonData data) {
        this.canvas = canvas;
        this.data = data;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (data.draw && code == KeyEvent.VK_P) {
            data.fill = !data.fill;
        } else if(data.draw && code == KeyEvent.VK_K) {
            data.convexity = !data.convexity;
        } else if(code == KeyEvent.VK_N) {
            if(data.convexity) {
                if(!data.polygon.convexityTest().getKey()) {
                    data.polygon.removeLastPoint();
                }
            }

            data.draw = !data.draw;
            data.convexity = false;
            data.fill = false;
            if(data.draw) {
                data.polygon = new Polygon();
            }
        }

        canvas.repaint();
    }
}
