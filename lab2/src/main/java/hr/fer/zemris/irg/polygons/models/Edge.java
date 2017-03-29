package hr.fer.zemris.irg.polygons.models;

import java.awt.Point;

/**
 * Created by Dominik on 27.3.2017..
 */
public class Edge {
    public int a;
    public int b;
    public int c;

    public Edge(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }


    public static Edge getEdgeFromPoints(Point start, Point end) {
        int dy = end.y - start.y;
        int dx = end.x - start.x;

        int a = dy;
        int b = -dx;
        int c = start.x * (-dy) + start.y * dx;
        return new Edge(a, b, c);
    }
}
