package hr.fer.zemris.irg.lab1.linalg.demonstration;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Dominik on 14.3.2017..
 */
public class Third {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double[][] points = new double[3][];
        double[] t = null;

        System.out.println("Unos točke oblika: x y z");
        for(int i = 1; i <= 4; i++) {
            System.out.print("Unesite " + i + ". točku: ");
            String line = sc.nextLine();
            String[] split = line.trim().split("\\s+");

            double[] arr = Arrays.stream(split).mapToDouble(s -> Double.parseDouble(s.trim())).toArray();
            if(i == 4) {
                t = arr;
            } else {
                points[i - 1] = arr;
            }
        }

        IMatrix matrix = new Matrix(points, false).nTranspose(true);
        IMatrix vector = (new Vector(t)).toColumnMatrix(false);

        IMatrix result = matrix.nInvert().nMultiply(vector);
        System.out.println(result);
    }

}
