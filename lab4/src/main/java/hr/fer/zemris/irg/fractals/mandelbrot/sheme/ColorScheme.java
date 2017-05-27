package hr.fer.zemris.irg.fractals.mandelbrot.sheme;

/**
 * Created by Dominik on 27.5.2017..
 */
public class ColorScheme implements IColorScheme {
    private static ColorScheme INSTANCE;

    private ColorScheme() {
    }

    public static IColorScheme getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ColorScheme();
        }

        return INSTANCE;
    }

    @Override
    public double[] scheme(int n, int limit) {
        if (n == -1) {
            return BLACK;
        }

        int r, g, b;
        if (limit < 16) {
            r = (int) ((n - 1.0) / (limit - 1.0) * 255 + 0.5);
            g = 255 - r;
            b = ((n - 1) % (limit / 2)) * 255 / (limit / 2);
        } else {
            limit = limit < 32 ? limit : 32;
            r = (n - 1) * 255 / limit;
            g = ((n - 1) % (limit / 4)) * 255 / (limit / 4);
            b = ((n - 1) % (limit / 8)) * 255 / (limit / 8);
        }

        return new double[] { r / 255., g / 255., b / 255. };
    }
}
