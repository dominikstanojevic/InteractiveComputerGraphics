package hr.fer.zemris.irg.shapes3D;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.shapes3D.models.Vertex3D;

import java.util.Arrays;

/**
 * Created by Dominik on 31.3.2017..
 */
public class Utils {
    public static IVector getNormalForTriangle(Vertex3D first, Vertex3D second, Vertex3D third) {
        IVector firstAsVector = first.toVector();
        IVector f = second.toVector().sub(firstAsVector);
        IVector s = third.toVector().sub(firstAsVector);
        return f.nVectorProduct(s);
    }

    public static Vertex3D parseVertex(String line) {
        String[] coordinates = line.split("\\s+");
        if (coordinates.length != 3) {
            throw new RuntimeException("Invalid number of dimensions.");
        }

        double[] c = Arrays.stream(coordinates).mapToDouble(s -> Double.parseDouble(s.trim())).toArray();
        return new Vertex3D(c[0], c[1], c[2]);
    }

    public static IVector getCenterForTriangle(Vertex3D first, Vertex3D second, Vertex3D third) {
        IVector firstAsVector = first.toVector();
        IVector secondAsVector = second.toVector();
        IVector thirdAsVector = third.toVector();

        return firstAsVector.add(secondAsVector).add(thirdAsVector).scalarMultiply(1. / 3);
    }
}
