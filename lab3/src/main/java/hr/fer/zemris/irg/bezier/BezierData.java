package hr.fer.zemris.irg.bezier;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 8.5.2017..
 */
public class BezierData {
    public List<Point> points = new ArrayList<>();

    public Integer current = null;
    public static final int DIV = 100;
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    public int closeTo(Point point) {
        int index = -1;
        double distance = Double.POSITIVE_INFINITY;

        for (int i = 0, n = points.size(); i < n; i++) {
            Point p = points.get(i);
            if (point.x < p.x - WIDTH) {
                continue;
            }
            if (point.x > p.x + WIDTH) {
                continue;
            }
            if (point.y < p.y - HEIGHT) {
                continue;
            }
            if (point.y > p.y + HEIGHT) {
                continue;
            }

            int d = Math.abs(point.x - p.x) + Math.abs(point.y - p.y);
            if (d < distance) {
                index = i;
                distance = d;
            }
        }

        return index;
    }
}
