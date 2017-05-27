package hr.fer.zemris.irg.fractals.mandelbrot.sheme;

/**
 * Created by Dominik on 27.5.2017..
 */
public class GrayScheme implements IColorScheme {
    private static GrayScheme INSTANCE;

    private GrayScheme() {

    }

    public static IColorScheme getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GrayScheme();
        }

        return INSTANCE;
    }

    @Override
    public double[] scheme(int n, int limit) {
        if (n == -1) {
            return BLACK;
        } else {
            return WHITE;
        }
    }
}
