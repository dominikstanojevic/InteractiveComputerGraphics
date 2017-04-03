package hr.fer.zemris.irg.polygons.models;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import javafx.util.Pair;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 27.3.2017..
 */
public class Polygon {
    private List<PolygonElement> elements = new ArrayList<>();
    private int nVertices = 0;

    public void removeLastPoint() {
        elements.remove(elements.size() - 1);
        nVertices--;
    }

    public enum Orientation {
        CLOCKWISE,
        COUNTERCLOCKWISE
    }

    public void addTempPoint(Point point) {
        PolygonElement element;
        if (elements.size() == nVertices) {
            element = new PolygonElement(point);
            elements.add(element);
        } else {
            element = elements.get(nVertices);
            element.start = point;
        }
    }


    private void polygonCoef() {
        int n = elements.size();
        int start = n - 1;
        for (int i = 0; i < n; start = i++) {
            PolygonElement element = elements.get(start);
            PolygonElement next = elements.get(i);
            element.edge = Edge.getEdgeFromPoints(next.start, element.start);
            element.left = element.start.y < next.start.y;
        }
    }

    public void addPoint(Point point, boolean checkConvex) {
        addTempPoint(point);
        if (checkConvex && nVertices >= 2) {
            Pair<Boolean, Orientation> test = convexityTest();
            if (!test.getKey()) {
                System.out.println("If point (" + point.x + ", " + point.y + ") is added, polygon won't be convex.");
                return;
            }
        }
        nVertices = elements.size();
    }

    public void draw(GL2 gl2) {
        gl2.glColor3i(0, 0, 0);
        gl2.glBegin(GL2.GL_LINE_LOOP);
        for (PolygonElement e : elements) {
            gl2.glVertex2i(e.start.x, e.start.y);
        }
        gl2.glEnd();
    }


    public enum Relation {
        INSIDE, OUTSIDE;

        public String relation(Point point) {
            return "Point (" + point.x + ", " + point.y + ") is " + this.name().toLowerCase() + ".";
        }
    }

    public Relation dotTest(Point point) {
        Pair<Boolean, Orientation> test = convexityTest();


        int over = 0, under = 0, on = 0;
        for(PolygonElement e : elements) {
            int r = e.edge.a * point.x + e.edge.b * point.y + e.edge.c;
            if (r == 0) {
                on++;
            } else if (r > 0) {
                over++;
            } else {
                under++;
            }
        }

        if(test.getValue() == Orientation.COUNTERCLOCKWISE && under == 0) {
            return Relation.INSIDE;
        } else if (test.getValue() == Orientation.CLOCKWISE && over == 0) {
            return Relation.INSIDE;
        }

        return Relation.OUTSIDE;
    }

    public Pair<Boolean, Orientation> convexityTest() {
        polygonCoef();

        int n = elements.size();
        if (n < 2) {
            return new Pair<>(true, null);
        }

        int over = 0, under = 0, on = 0;
        int start = n - 2;

        for (int i = 0; i < n; i++, start++) {
            if (start >= n) {
                start = 0;
            }
            PolygonElement element = elements.get(start);
            PolygonElement next = elements.get(i);

            long r = element.edge.a * next.start.x + element.edge.b * next.start.y + element.edge.c;
            if (r == 0) {
                on++;
            } else if (r > 0) {
                over++;
            } else {
                under++;
            }
        }

        if (under == 0) {
            return new Pair<>(true, Orientation.COUNTERCLOCKWISE);
        } else if (over == 0) {
            return new Pair<>(true, Orientation.CLOCKWISE);
        }

        return new Pair<>(false, null);
    }

    public void drawFill(GL2 gl2) {
        polygonCoef();
        Pair<Boolean, Orientation> convex = convexityTest();

        int n = elements.size();
        int xmin = Integer.MAX_VALUE, xmax = Integer.MIN_VALUE;
        int ymin = Integer.MAX_VALUE, ymax = Integer.MIN_VALUE;
        for (PolygonElement e : elements) {
            if (xmin > e.start.x) {
                xmin = e.start.x;
            }
            if (xmax < e.start.x) {
                xmax = e.start.x;
            }
            if (ymin > e.start.y) {
                ymin = e.start.y;
            }
            if (ymax < e.start.y) {
                ymax = e.start.y;
            }
        }

        for (int y = ymin; y <= ymax; y++) {
            double L = xmin, R = xmax;
            int start = n - 1;
            for (int i = 0; i < n; start = i++) {
                PolygonElement element = elements.get(start);
                PolygonElement next = elements.get(i);
                if (element.edge.a == 0) {
                    if (element.start.y == y) {
                        if (element.start.x < next.start.x) {
                            L = element.start.x;
                            R = next.start.x;
                        } else {
                            L = next.start.x;
                            R = element.start.x;
                        }

                        break;
                    }
                } else {
                    double x = (-element.edge.b * y - element.edge.c) / (double) element.edge.a;
                    if (element.left ^ (convex.getValue() == Orientation.COUNTERCLOCKWISE)) {
                        if (L < x) {
                            L = x;
                        }
                    } else {
                        if (R > x) {
                            R = x;
                        }
                    }
                }
            }

            gl2.glColor3i(0, 0, 0);
            gl2.glBegin(GL.GL_LINES);
            gl2.glVertex2i((int) Math.round(L), y);
            gl2.glVertex2i((int) Math.round(R), y);
            gl2.glEnd();
        }
    }
}
