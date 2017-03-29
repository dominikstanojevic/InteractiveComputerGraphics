package hr.fer.zemris.irg.polygons.models;

import java.awt.Point;

/**
 * Created by Dominik on 27.3.2017..
 */
public class PolygonElement {
    public Point start;
    public Edge edge;
    public Boolean left;

    public PolygonElement(Point start) {
        this.start = start;
    }
}
