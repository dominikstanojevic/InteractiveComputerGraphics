package hr.fer.zemris.irg.lab1.first.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.lab1.first.ColorContainer;
import hr.fer.zemris.irg.lab1.first.Data;
import hr.fer.zemris.irg.lab1.first.Triangle;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dominik on 15.3.2017..
 */
public class ExampleMouseListener extends MouseAdapter{
    private Data data;
    private GLCanvas canvas;

    public ExampleMouseListener(Data data, GLCanvas canvas) {
        this.data = data;
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (data.currentTriangle == null) {
            Color color = ColorContainer.getInstance().getColor(data.colorIndex);
            Point point = e.getPoint();
            data.currentTriangle = new Triangle(color, point);
            data.nPoints = 1;
        } else if (data.nPoints == 1) {
            data.currentTriangle.addPoint(e.getPoint(), 1);
            data.nPoints = 2;
        } else if (data.nPoints == 2) {
            data.currentTriangle.addPoint(e.getPoint(), 2);
            data.triangles.add(data.currentTriangle);
            data.currentTriangle = null;
            data.nPoints = 0;
        }

        canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(data.nPoints == 1) {
            data.currentTriangle.addPoint(e.getPoint(), 1);
        } else if (data.nPoints == 2) {
            data.currentTriangle.addPoint(e.getPoint(), 2);
        }

        canvas.repaint();
    }
}
