package hr.fer.zemris.irg.lab1.bresenham.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.lab1.bresenham.BresenhamData;
import hr.fer.zemris.irg.lab1.bresenham.Line;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dominik on 17.3.2017..
 */
public class BresenhamMouseListener extends MouseAdapter {
    private GLCanvas canvas;
    private BresenhamData data;

    public BresenhamMouseListener(GLCanvas canvas, BresenhamData data) {
        this.canvas = canvas;
        this.data = data;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        point.y = canvas.getSurfaceHeight() - point.y;
        if(data.temp == null) {
            data.temp = point;
        } else {
            if(data.temp.equals(point)) {
                return;
            }

            Line line = new Line(data.temp, point);
            data.temp = null;
            data.lines.add(line);
            canvas.repaint();
        }
    }
}
