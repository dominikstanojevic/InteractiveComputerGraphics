package hr.fer.zemris.irg.bezier.listeners;

import hr.fer.zemris.irg.bezier.BezierData;

import javax.swing.SwingUtilities;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dominik on 8.5.2017..
 */
public class BezierMouseListener extends MouseAdapter {
    private BezierData data;
    private Canvas canvas;

    public BezierMouseListener(BezierData data, Canvas canvas) {
        this.data = data;
        this.canvas = canvas;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Point p = e.getPoint();
            if (!data.points.contains(p)) {
                data.points.add(p);
                canvas.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            Point point = e.getPoint();
            int index = data.closeTo(point);
            if (index != -1) {
                data.current = index;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            data.current = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (data.current != null && SwingUtilities.isRightMouseButton(e)) {
            Point p = e.getPoint();
            data.points.set(data.current, p);
            canvas.repaint();
        }
    }
}
