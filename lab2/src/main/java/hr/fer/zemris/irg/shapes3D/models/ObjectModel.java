package hr.fer.zemris.irg.shapes3D.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.shapes3D.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Dominik on 31.3.2017..
 */
public class ObjectModel {
    private Vertex3D[] vertices;
    private Face3D[] triangles;

    public ObjectModel(Vertex3D[] vertices, Face3D[] triangles) {
        this.vertices = vertices;
        this.triangles = triangles;
    }

    public ObjectModel copy() {
        Vertex3D[] verticesCopy = Arrays.copyOf(vertices, vertices.length);
        Face3D[] trianglesCopy = Arrays.copyOf(triangles, triangles.length);
        return new ObjectModel(verticesCopy, trianglesCopy);
    }

    public String dumpToOBJ() {
        StringJoiner sj = new StringJoiner("\n");
        for (Vertex3D vertex : vertices) {
            sj.add("v " + vertex.x + " " + vertex.y + " " + vertex.z);
        }
        for (Face3D triangle : triangles) {
            sj.add("f " + triangle.getIndex(0) + " " + triangle.getIndex(1) + " " + triangle.getIndex(2));
        }

        return sj.toString();
    }

    public void normalize() {
        double xmin = Double.POSITIVE_INFINITY, ymin = Double.POSITIVE_INFINITY, zmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY, ymax = Double.NEGATIVE_INFINITY, zmax = Double.NEGATIVE_INFINITY;
        for (Vertex3D vertex : vertices) {
            if (vertex.x < xmin) {
                xmin = vertex.x;
            }
            if (vertex.x > xmax) {
                xmax = vertex.x;
            }
            if (vertex.y < ymin) {
                ymin = vertex.y;
            }
            if (vertex.y > ymax) {
                ymax = vertex.y;
            }
            if (vertex.z < zmin) {
                zmin = vertex.z;
            }
            if (vertex.z > zmax) {
                zmax = vertex.z;
            }
        }

        double x = (xmin + xmax) / 2;
        double y = (ymin + ymax) / 2;
        double z = (zmin + zmax) / 2;

        double range = Math.max(xmax - xmin, Math.max(ymax - ymin, zmax - zmin));
        for (Vertex3D vertex : vertices) {
            vertex.x = 2 * (vertex.x - x) / range;
            vertex.y = 2 * (vertex.y - y) / range;
            vertex.z = 2 * (vertex.z - z) / range;
        }
    }

    public enum Position {
        INSIDE, OUTSIDE, ON
    }

    public Position getVertexPosition(Vertex3D vertex) {
        int under = 0, above = 0, on = 0;
        for (Face3D triangle : triangles) {
            Vertex3D v = vertices[triangle.getIndex(0) - 1];
            IVector normal = Utils.getNormalForTriangle(v, vertices[triangle.getIndex(1) - 1],
                    vertices[triangle.getIndex(2) - 1]);

            double d = -normal.get(0) * v.x - normal.get(1) * v.y - normal.get(2) * v.z;
            double r = normal.get(0) * vertex.x + normal.get(1) * vertex.y + normal.get(2) * vertex.z + d;
            if (r > 0) {
                above++;
            } else if (r < 0) {
                under++;
            } else {
                on++;
            }
        }

        if (above == 0) {
            if (on == 0) {
                return Position.INSIDE;
            } else {
                return Position.ON;
            }
        } else {
            return Position.OUTSIDE;
        }
    }

    public Face3D[] getTriangles() {
        return triangles;
    }

    public Vertex3D getVertex3DForIndex(int index) {
        if (index <= 0 || index > vertices.length) {
            throw new RuntimeException("Invalid index: " + index + ".");
        }

        return vertices[index - 1];
    }

    public static ObjectModel parse(Scanner input) {
        List<Vertex3D> vertices = new ArrayList<>();
        List<Face3D> triangles = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.startsWith("v ")) {
                Vertex3D vertex = Utils.parseVertex(line.replace("v ", "").trim());
                vertices.add(vertex);
            } else if (line.startsWith("f ")) {
                Face3D triangle = parseTriangle(line.replace("f ", "").trim());
                triangles.add(triangle);
            }
        }

        return new ObjectModel(vertices.toArray(new Vertex3D[vertices.size()]),
                triangles.toArray(new Face3D[triangles.size()]));
    }

    private static Face3D parseTriangle(String line) {
        String[] indexes = line.split("\\s+");
        if (indexes.length != 3) {
            throw new RuntimeException("Invalid number of indexes.");
        }

        int[] i = Arrays.stream(indexes).mapToInt(s -> Integer.parseInt(s.trim())).toArray();
        return new Face3D(i[0], i[1], i[2]);
    }
}
