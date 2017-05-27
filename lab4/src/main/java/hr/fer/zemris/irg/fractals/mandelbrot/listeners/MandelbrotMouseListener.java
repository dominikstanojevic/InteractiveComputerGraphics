package hr.fer.zemris.irg.fractals.mandelbrot.listeners;

import com.jogamp.opengl.awt.GLCanvas;
import hr.fer.zemris.irg.fractals.mandelbrot.Complex;
import hr.fer.zemris.irg.fractals.mandelbrot.MandelbrotData;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dominik on 27.5.2017..
 */
public class MandelbrotMouseListener extends MouseAdapter {
    private GLCanvas canvas;
    private MandelbrotData data;

    public MandelbrotMouseListener(GLCanvas canvas, MandelbrotData data) {
        this.canvas = canvas;
        this.data = data;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();

        int width = canvas.getSurfaceWidth();
        int height = canvas.getSurfaceHeight();

        Complex c = data.getComplex(point.x, point.y, width, height);
        MandelbrotData.ComplexPlane coordinates = data.getCoordinates();

        double w = (coordinates.uMax - coordinates.uMin) / 32;
        double h = (coordinates.vMax - coordinates.vMin) / 32;

        MandelbrotData.ComplexPlane newCoordinates =
                new MandelbrotData.ComplexPlane(c.getReal() - w, c.getReal() + w, c.getImaginary() - h,
                        c.getImaginary() + h);
        data.setCoordinates(newCoordinates);

        canvas.repaint();
    }
}
