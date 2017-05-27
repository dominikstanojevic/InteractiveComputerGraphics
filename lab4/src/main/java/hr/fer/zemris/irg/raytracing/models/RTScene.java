package hr.fer.zemris.irg.raytracing.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.raytracing.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Dominik on 24.5.2017..
 */
public class RTScene {
    public IVector eye;
    public IVector view;
    public IVector viewUp;
    public double h;
    public double xAngle;
    public double yAngle;
    public double[] gaIntensity = new double[] { 0, 0, 0 };
    public Light[] sources;
    public SceneObject[] objects;

    public IVector xAxis;
    public IVector yAxis;
    public double horizontal;
    public double vertical;
    public IVector G;

    private void computeKS() {
        G = eye.nAdd(view.nNormalize().scalarMultiply(h));
        IVector diff = G.nSub(eye).normalize();

        yAxis = viewUp.nSub(diff.nScalarMultiply(diff.scalarProduct(viewUp))).normalize();
        xAxis = diff.nVectorProduct(yAxis).normalize();

        horizontal = h * Math.tan(xAngle / 2) * 4;
        vertical = h * Math.tan(yAngle / 2) * 4;
    }

    public static RTScene loadScene(Path path) {
        try (Scanner sc = new Scanner(path)) {
            RTScene scene = new RTScene();
            List<Light> lights = new ArrayList<>();
            List<SceneObject> objects = new ArrayList<>();
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("#")) {
                    continue;
                }
                int index = line.indexOf("#");
                if (index != -1) {
                    line = line.substring(0, index);
                }

                if (line.startsWith("e")) {
                    scene.eye = Utils.parseVector(line.replaceFirst("e", ""), 3);
                } else if (line.startsWith("vu")) {
                    scene.viewUp = Utils.parseVector(line.replaceFirst("vu", ""), 3).normalize();
                } else if (line.startsWith("v")) {
                    scene.view = Utils.parseVector(line.replaceFirst("v", ""), 3);
                } else if (line.startsWith("h")) {
                    scene.h = Double.parseDouble(line.replaceFirst("h", "").trim());
                } else if (line.startsWith("xa")) {
                    scene.xAngle = Math.toRadians(Double.parseDouble(line.replaceFirst("xa", "").trim()));
                } else if (line.startsWith("ya")) {
                    scene.yAngle = Math.toRadians(Double.parseDouble(line.replaceFirst("ya", "").trim()));
                } else if (line.startsWith("ga")) {
                    scene.gaIntensity = Utils.parseDoubleArray(line.replaceFirst("ga", ""), 3);
                } else if (line.startsWith("i")) {
                    lights.add(Light.parse(line.replaceFirst("i", "")));
                } else if (line.startsWith("o")) {
                    objects.add(SceneObject.parseObject(line.replaceFirst("o", "").trim()));
                } else {
                    throw new RuntimeException("Invalid command.");
                }
            }

            scene.sources = lights.toArray(new Light[lights.size()]);
            scene.objects = objects.toArray(new SceneObject[objects.size()]);

            scene.computeKS();
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
