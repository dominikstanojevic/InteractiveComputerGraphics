package hr.fer.zemris.irg.raytracing.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;

/**
 * Created by Dominik on 24.5.2017..
 */
public class Intersection {
    public SceneObject object;
    public double lambda;
    public boolean front;
    public IVector point;
}
