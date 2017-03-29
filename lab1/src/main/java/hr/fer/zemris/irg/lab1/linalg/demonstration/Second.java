package hr.fer.zemris.irg.lab1.linalg.demonstration;

import hr.fer.zemris.irg.lab1.linalg.matrices.IMatrix;
import hr.fer.zemris.irg.lab1.linalg.matrices.Matrix;
import hr.fer.zemris.irg.lab1.linalg.vectors.Vector;

import java.util.Scanner;

/**
 * Created by Dominik on 14.3.2017..
 */
public class Second {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double[][] left = new double[3][3];
        double[] right = new double[3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                left[i][j] = sc.nextDouble();
            }

            right[i] = sc.nextDouble();
        }

        IMatrix input = new Matrix(left, false);
        IMatrix output = (new Vector(right)).toColumnMatrix();

        IMatrix result = input.nInvert().nMultiply(output);
        System.out.println(result);
    }
}
