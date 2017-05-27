package hr.fer.zemris.irg.raytracing.models;

import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;

/**
 * Created by Dominik on 25.5.2017..
 */
public class Ray {
    public IVector start;
    public IVector direction;

    public Ray(IVector start, IVector direction) {
        this.start = start;
        this.direction = direction.nNormalize();
    }
}
