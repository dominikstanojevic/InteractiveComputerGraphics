package hr.fer.zemris.irg.lab1.linalg.vectors;

/**
 * Created by Dominik on 13.3.2017..
 */
public class Example {
    public static void main(String[] args) {
        IVector a = Vector.parseSimple("1 0 0");
        IVector b = Vector.parseSimple("5 0 0");
        IVector c = Vector.parseSimple("3 8 0");

        IVector t = Vector.parseSimple("3 4 0");

        double pov = b.nSub(a).nVectorProduct(c.nSub(a)).norm() / 2;
        double povA = b.nSub(t).nVectorProduct(c.nSub(t)).norm() / 2;
        double povB = a.nSub(t).nVectorProduct(c.nSub(t)).norm() / 2;
        double povC = a.nSub(t).nVectorProduct(b.nSub(t)).norm() / 2;

        double t1 = povA / pov;
        double t2 = povB / pov;
        double t3 = povC / pov;

        System.out.println("Baricentricne koordinate su: (" + t1 + ", " + t2 + ", " + t3 + ").");
    }
}
