package hr.fer.zemris.irg.fractals.mandelbrot;

import hr.fer.zemris.irg.fractals.mandelbrot.sheme.GrayScheme;
import hr.fer.zemris.irg.fractals.mandelbrot.sheme.IColorScheme;

import java.util.Stack;

/**
 * Created by Dominik on 27.5.2017..
 */
public class MandelbrotData {
    public int order = 2;
    public IColorScheme scheme = GrayScheme.getInstance();
    private ComplexPlane coordinates = new ComplexPlane();
    public int limit = 128;

    private Stack<ComplexPlane> stack = new Stack<>();

    public ComplexPlane getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ComplexPlane coordinates) {
        stack.push(this.coordinates);
        this.coordinates = coordinates;
    }

    public boolean restoreCoordinates() {
        if (!stack.isEmpty()) {
            this.coordinates = stack.pop();
            return true;
        }

        return false;
    }

    public Complex getComplex(int x, int y, int width, int height) {
        double u = ((double) x) / width * (coordinates.uMax - coordinates.uMin) + coordinates.uMin;
        double v = ((double) y) / height * (coordinates.vMax - coordinates.vMin) + coordinates.vMin;

        return new Complex(u, v);
    }

    public static class ComplexPlane {
        public double uMin;
        public double uMax;
        public double vMin;
        public double vMax;

        public ComplexPlane() {
            this(-2, 1, -1.2, 1.2);
        }

        public ComplexPlane(double uMin, double uMax, double vMin, double vMax) {
            this.uMin = uMin;
            this.uMax = uMax;
            this.vMin = vMin;
            this.vMax = vMax;
        }
    }
}
