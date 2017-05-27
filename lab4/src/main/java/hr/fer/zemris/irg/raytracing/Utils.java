package hr.fer.zemris.irg.raytracing;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Dominik on 24.5.2017..
 */
public class Utils {
    public static double[] parseDoubleArray(String line, int dimension) {
        String[] values = line.trim().split("\\s+");
        if (values.length != dimension) {
            throw new RuntimeException("Invalid dimensions.");
        }

        return Arrays.stream(values).mapToDouble(Double::parseDouble).toArray();
    }

    public static IVector parseVector(String line, int dimension) {
        double[] array = parseDoubleArray(line, dimension);
        return new Vector(array);
    }

    public static double[] elementsFromRange(String[] data, int start, int end) {
        return IntStream.range(start, end).mapToDouble(i -> Double.parseDouble(data[i].trim())).toArray();
    }
}
