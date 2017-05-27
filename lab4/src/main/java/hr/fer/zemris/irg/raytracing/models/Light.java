package hr.fer.zemris.irg.raytracing.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

import java.util.stream.IntStream;

/**
 * Created by Dominik on 24.5.2017..
 */
public class Light {
    public IVector position;
    public double[] rgb;

    public Light(IVector position, double[] rgb) {
        this.position = position;
        this.rgb = rgb;
    }

    public static Light parse(String line) {
        String[] data = line.trim().split("\\s+");
        if (data.length != 6) {
            throw new RuntimeException("Invalid dimension");
        }

        IVector position = new Vector(IntStream.range(0, 3).mapToDouble(i -> Double.parseDouble(data[i])).toArray());
        double[] rgb = IntStream.range(3, 6).mapToDouble(i -> Double.parseDouble(data[i])).toArray();

        return new Light(position, rgb);
    }
}
