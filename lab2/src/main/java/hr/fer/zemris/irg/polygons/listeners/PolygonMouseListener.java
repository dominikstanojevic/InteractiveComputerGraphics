package hr.fer.zemris.irg.polygons.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.polygons.PolygonData;
import hr.fer.zemris.irg.polygons.models.Polygon;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dominik on 27.3.2017..
 */
public class PolygonMouseListener extends MouseAdapter {
    private GLCanvas glCanvas;
    private PolygonData data;

    public PolygonMouseListener(GLCanvas glCanvas, PolygonData data) {
        this.glCanvas = glCanvas;
        this.data = data;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(data.draw) {
            Point point = getPoint(e);
            data.polygon.addPoint(point, data.convexity);
        } else {
            Point point = getPoint(e);
            Polygon.Relation relation = data.polygon.dotTest(point);
            System.out.println(relation.relation(point));
        }

        glCanvas.repaint();
    }

    private Point getPoint(MouseEvent e) {
        Point point = e.getPoint();
        point.y = glCanvas.getSurfaceHeight() - point.y;
        return point;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (data.draw) {
            Point point = getPoint(e);
            data.polygon.addTempPoint(point);
        }

        glCanvas.repaint();
    }
}
