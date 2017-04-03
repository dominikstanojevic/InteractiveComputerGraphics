package hr.fer.zemris.irg.shapes3D.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

/**
 * Created by Dominik on 31.3.2017..
 */
public class Vertex3D {
    public double x;
    public double y;
    public double z;

    public Vertex3D(){}

    public Vertex3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertex3D(Vertex3D vertex) {
        this(vertex.x, vertex.y, vertex.z);
    }

    public IVector toVector() {
        return new Vector(new double[]{x, y, z});
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
