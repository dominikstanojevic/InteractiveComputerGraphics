package hr.fer.zemris.irg.raytracing.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;
import hr.fer.zemris.irg.raytracing.Utils;

/**
 * Created by Dominik on 24.5.2017..
 */
public class Sphere extends SceneObject {
    public IVector center;
    public double radius;

    public static Sphere parse(String line) {
        String[] data = line.trim().split("\\s+");
        if (data.length != 15) {
            throw new RuntimeException("Invalid number of arguments.");
        }

        Sphere sphere = new Sphere();
        sphere.center = new Vector(Utils.elementsFromRange(data, 0, 3));
        sphere.radius = Double.parseDouble(data[3].trim());

        sphere.fambRGB = sphere.bambRGB = Utils.elementsFromRange(data, 4, 7);
        sphere.fdifRGB = sphere.bdifRGB = Utils.elementsFromRange(data, 7, 10);
        sphere.frefRGB = sphere.brefRGB = Utils.elementsFromRange(data, 10, 13);
        sphere.fn = sphere.bn = Double.parseDouble(data[13].trim());
        sphere.fkref = sphere.bkref = Double.parseDouble(data[14].trim());

        return sphere;
    }

    @Override
    public void updateIntersection(
            Intersection inters, IVector start, IVector d) {
        IVector diff = start.nSub(center);

        double b = 2 *d.scalarProduct(diff);
        double c = diff.scalarProduct(diff) - radius * radius;

        double discriminant = Math.sqrt(b * b - 4 * c);
        if (Double.isNaN(discriminant)) {
            return;
        }

        double lambda1 = (-b + discriminant) / 2;
        double lambda2 = (-b - discriminant) / 2;
        if (lambda1 < 0 && lambda2 < 0) {
            return;
        }

        double distance = lambda1;
        if (lambda1 > 0 && lambda2 > 0) {
            distance = Math.min(lambda1, lambda2);
        } else if (lambda1 < 0 && lambda2 > 0) {
            distance = lambda2;
        }

        IVector point = start.nAdd(d.nScalarMultiply(distance));

        if ((inters.object == null || inters.lambda > distance)) {
            inters.lambda = distance;
            inters.object = this;
            inters.point = point;
            inters.front = true;
        }
    }

    @Override
    public IVector getNormalInPoint(IVector point) {
        return point.nSub(center).normalize();
    }
}
