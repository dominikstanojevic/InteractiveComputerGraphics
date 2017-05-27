package hr.fer.zemris.irg.raytracing.models;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;
import hr.fer.zemris.irg.raytracing.Utils;

/**
 * Created by Dominik on 24.5.2017..
 */
public class Patch extends SceneObject {
    public IVector center;
    public IVector v1;
    public IVector v2;
    public IVector normal;
    public double w;
    public double h;

    public static Patch parse(String line) {
        String[] data = line.trim().split("\\s+");
        if (data.length != 33) {
            throw new RuntimeException("Invalid arguments.");
        }

        Patch patch = new Patch();

        patch.center = new Vector(Utils.elementsFromRange(data, 0, 3));
        patch.v1 = new Vector(Utils.elementsFromRange(data, 3, 6));
        patch.v2 = new Vector(Utils.elementsFromRange(data, 6, 9));
        patch.w = Double.parseDouble(data[9].trim());
        patch.h = Double.parseDouble(data[10].trim());

        patch.normal = patch.v1.nVectorProduct(patch.v2);

        patch.fambRGB = Utils.elementsFromRange(data, 11, 14);
        patch.fdifRGB = Utils.elementsFromRange(data, 14, 17);
        patch.frefRGB = Utils.elementsFromRange(data, 17, 20);
        patch.fn = Double.parseDouble(data[20].trim());
        patch.fkref = Double.parseDouble(data[21].trim());

        patch.bambRGB = Utils.elementsFromRange(data, 22, 25);
        patch.bdifRGB = Utils.elementsFromRange(data, 25, 28);
        patch.brefRGB = Utils.elementsFromRange(data, 28, 31);
        patch.bn = Double.parseDouble(data[31].trim());
        patch.bkref = Double.parseDouble(data[32].trim());

        return patch;
    }

    @Override
    public void updateIntersection(
            Intersection inters, IVector start, IVector d) {
        IMatrix M = new Matrix(new double[][] {
                { v1.get(0), v2.get(0), -d.get(0) }, { v1.get(1), v2.get(1), -d.get(1) }, {
                v1.get(2), v2.get(2), -d.get(2) } }, false);
        IMatrix V = new Vector(new double[] {
                start.get(0) - center.get(0), start.get(1) - center.get(1), start.get(2) - center.get(2) })
                .toColumnMatrix(true);

        //lambda, mu, epsilon
        IVector result = M.nInvert().nMultiply(V).toVector(true);

        double epsilon = result.get(2);
        if (epsilon < 0) {
            return;
        }

        double lambda = result.get(0);
        double halfWidth = w / 2;
        if (lambda < -halfWidth || lambda > halfWidth) {
            return;
        }

        double mu = result.get(1);
        double halfHeight = h / 2;
        if (mu < -halfHeight || mu > halfHeight) {
            return;
        }

        IVector point = start.nAdd(d.nScalarMultiply(epsilon));

        if (inters.object == null || inters.lambda > epsilon) {
            inters.lambda = epsilon;
            inters.object = this;
            inters.front = d.cosine(normal) < 0;
            inters.point = point;
        }
    }

    @Override
    public IVector getNormalInPoint(IVector point) {
        return normal.copy();
    }
}
