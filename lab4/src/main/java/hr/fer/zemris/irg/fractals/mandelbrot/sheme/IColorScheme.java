package hr.fer.zemris.irg.fractals.mandelbrot.sheme;

/**
 * Created by Dominik on 27.5.2017..
 */
public interface IColorScheme {
    double[] scheme(int n, int limit);

    double[] BLACK = new double[] { 0, 0, 0 };
    double[] WHITE = new double[] { 1, 1, 1 };
}
