package hr.fer.zemris.irg.raytracing.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;

/**
 * Created by Dominik on 24.5.2017..
 */
public abstract class SceneObject {
    public double[] fambRGB;
    public double[] fdifRGB;
    public double[] frefRGB;
    public double fn;
    public double fkref;

    public double[] bambRGB;
    public double[] bdifRGB;
    public double[] brefRGB;
    public double bn;
    public double bkref;

    public abstract void updateIntersection(Intersection inters, IVector start, IVector d);

    public abstract IVector getNormalInPoint(IVector point);

    public static SceneObject parseObject(String line) {
        if (line.startsWith("p")) {
            return Patch.parse(line.replaceFirst("p", ""));
        } else if (line.startsWith("s")) {
            return Sphere.parse(line.replaceFirst("s", ""));
        } else {
            throw new RuntimeException("Invalid object.");
        }
    }
}
