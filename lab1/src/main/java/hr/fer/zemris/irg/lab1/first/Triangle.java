package hr.fer.zemris.irg.lab1.first;

import com.jogamp.opengl.GL2;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 15.3.2017..
 */
public class Triangle {
    private List<Point> points = new ArrayList<>();
    private Color color;

    public Triangle(Color color, Point point) {
        this.color = color;
        points.add(point);
    }


    public void addPoint(Point point, int index) {
        if (index < 0 || index >= 3) {
            throw new IndexOutOfBoundsException();
        }

        if(points.size() > index) {
            points.set(index, point);
        } else {
            points.add(point);
        }
    }

    public void display(GL2 gl2) {
        gl2.glColor3d(color.getRed(), color.getGreen(), color.getBlue());

        if(points.size() == 2) {
            gl2.glBegin(GL2.GL_LINE_STRIP);
        } else {
            gl2.glBegin(GL2.GL_TRIANGLES);
        }
        for (Point point : points) {
            gl2.glVertex2i(point.x, point.y);
        }
        gl2.glEnd();
    }
}
