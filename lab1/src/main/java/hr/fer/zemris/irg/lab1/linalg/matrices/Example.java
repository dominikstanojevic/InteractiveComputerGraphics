package hr.fer.zemris.irg.lab1.linalg.matrices;

/**
 * Created by Dominik on 14.3.2017..
 */
public class Example {
    public static void main(String[] args) {
        IMatrix a = Matrix.parseSimple("3 5 | 2 10");
        IMatrix r = Matrix.parseSimple("2 | 8");
        IMatrix v = a.nInvert().nMultiply(r);
        System.out.println("Rjesenje sustava je: ");
        System.out.println(v);
    }
}
