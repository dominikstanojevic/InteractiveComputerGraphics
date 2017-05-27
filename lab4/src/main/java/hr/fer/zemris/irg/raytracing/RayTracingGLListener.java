package hr.fer.zemris.irg.raytracing;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.raytracing.models.Intersection;
import hr.fer.zemris.irg.raytracing.models.Light;
import hr.fer.zemris.irg.raytracing.models.RTScene;
import hr.fer.zemris.irg.raytracing.models.Ray;
import hr.fer.zemris.irg.raytracing.models.SceneObject;

/**
 * Created by Dominik on 25.5.2017..
 */
public class RayTracingGLListener implements GLEventListener {
    private RTScene scene;

    public static final double[] BLACK = new double[] { 0, 0, 0 };
    public static final int DEFAULT_DEPTH = 1;

    public RayTracingGLListener(RTScene scene) {
        this.scene = scene;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();

        rayTrace(gl2, width, height);
    }

    private void rayTrace(GL2 gl2, int width, int height) {
        IVector corner = calculateCorner();

        gl2.glBegin(GL.GL_POINTS);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                setColor(gl2, i, j, trace(calculateRay(corner, i, j, width, height), DEFAULT_DEPTH));
            }
        }
        gl2.glEnd();

        gl2.glFlush();
    }

    private void setColor(GL2 gl2, int i, int j, double[] trace) {
        gl2.glColor3d(trace[0] > 255 ? 255 : trace[0], trace[1] > 255 ? 255 : trace[1],
                trace[2] > 255 ? 255 : trace[2]);
        gl2.glVertex2i(i, j);
    }

    private IVector calculateCorner() {
        IVector totalHorizontal = scene.xAxis.nScalarMultiply(scene.horizontal / 2);
        IVector totalVertical = scene.yAxis.nScalarMultiply(scene.vertical / 2);

        return scene.G.nSub(totalHorizontal).add(totalVertical);
    }

    private Ray calculateRay(IVector corner, int x, int y, int width, int height) {
        IVector totalHorizontal = scene.xAxis.nScalarMultiply(x * scene.horizontal / (width - 1));
        IVector totalVertical = scene.yAxis.nScalarMultiply(y * scene.vertical / (height - 1));

        IVector pp = corner.nAdd(totalHorizontal).sub(totalVertical);
        return new Ray(scene.eye, pp.sub(scene.eye).normalize());
    }

    private double[] trace(Ray ray, int depth) {
        Intersection intersection = findClosest(ray);

        if (intersection.object != null) {
            return calculateColor(intersection, ray, depth);
        } else {
            return BLACK;
        }
    }

    private Intersection findClosest(Ray ray) {
        Intersection intersection = new Intersection();

        for (SceneObject object : scene.objects) {
            object.updateIntersection(intersection, ray.start, ray.direction);
        }

        return intersection;
    }

    private double[] calculateColor(Intersection intersection, Ray ray, int depth) {
        double[] color = new double[3];
        addAmbient(color, intersection, scene.gaIntensity);
        for (Light light : scene.sources) {
            Ray tempRay = new Ray(light.position, intersection.point.nSub(light.position).normalize());
            Intersection closest = findClosest(tempRay);
            if (closest.object != null) {
                double distLightInter = light.position.nSub(intersection.point).norm();
                if (closest.lambda - distLightInter >= -1e-6) {
                    addDiffuse(color, light, closest);
                    addReflective(color, light, closest);
                }
            }
        }

        if (depth > 0) {
            Ray reflected = getReflectedRay(intersection, ray);
            if (intersection.front) {
                double[] rgb = trace(reflected, depth - 1);
                color[0] += intersection.object.fkref * rgb[0];
                color[1] += intersection.object.fkref * rgb[1];
                color[2] += intersection.object.fkref * rgb[2];
            } else {
                double[] rgb = trace(reflected, depth - 1);
                color[0] += intersection.object.bkref * rgb[0];
                color[1] += intersection.object.bkref * rgb[1];
                color[2] += intersection.object.bkref * rgb[2];
            }
        }

        return color;
    }

    private Ray getReflectedRay(Intersection intersection, Ray ray) {
        IVector direction = ray.direction.nSub(intersection.object.getNormalInPoint(intersection.point).nScalarMultiply(
                2 * ray.direction.scalarProduct(intersection.object.getNormalInPoint(intersection.point)))).normalize();
        return new Ray(intersection.point.nAdd(direction.nScalarMultiply(1e-4)), direction);
    }

    private void addReflective(double[] color, Light light, Intersection closest) {
        IVector normal = closest.object.getNormalInPoint(closest.point);
        IVector lightVector = light.position.nSub(closest.point).normalize();
        IVector reflected = normal.nScalarMultiply(2 * lightVector.scalarProduct(normal)).sub(lightVector).normalize();
        IVector view = scene.eye.nSub(closest.point).normalize();

        double scalar = reflected.scalarProduct(view);
        if (scalar < 0) {
            scalar = 0;
        }

        if (closest.front) {
            double value = Math.pow(scalar, closest.object.fn);

            color[0] += light.rgb[0] * closest.object.frefRGB[0] * value;
            color[1] += light.rgb[1] * closest.object.frefRGB[1] * value;
            color[2] += light.rgb[2] * closest.object.frefRGB[2] * value;
        } else {
            double value = Math.pow(scalar, closest.object.bn);

            color[0] += light.rgb[0] * closest.object.brefRGB[0] * value;
            color[1] += light.rgb[1] * closest.object.brefRGB[1] * value;
            color[2] += light.rgb[2] * closest.object.brefRGB[2] * value;
        }

    }

    private void addAmbient(double[] color, Intersection intersection, double[] intensity) {
        if (intersection.front) {
            color[0] += intensity[0] * intersection.object.fambRGB[0];
            color[1] += intensity[1] * intersection.object.fambRGB[1];
            color[2] += intensity[2] * intersection.object.fambRGB[2];
        } else {
            color[0] += intensity[0] * intersection.object.bambRGB[0];
            color[1] += intensity[1] * intersection.object.bambRGB[1];
            color[2] += intensity[2] * intersection.object.bambRGB[2];
        }
    }

    private void addDiffuse(double[] rgb, Light light, Intersection closest) {
        IVector normal = closest.object.getNormalInPoint(closest.point);
        IVector lightVector = light.position.nSub(closest.point).normalize();
        double max = Math.max(lightVector.scalarProduct(normal), 0);

        if (closest.front) {
            rgb[0] += light.rgb[0] * closest.object.fdifRGB[0] * max;
            rgb[1] += light.rgb[1] * closest.object.fdifRGB[1] * max;
            rgb[2] += light.rgb[2] * closest.object.fdifRGB[2] * max;
        } else {
            rgb[0] += light.rgb[0] * closest.object.bdifRGB[0] * max;
            rgb[1] += light.rgb[1] * closest.object.bdifRGB[1] * max;
            rgb[2] += light.rgb[2] * closest.object.bdifRGB[2] * max;
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl2.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluOrtho2D(0.0, width - 1, height - 1, 0);

        gl2.glViewport(0, 0, width, height);

        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();
    }
}
