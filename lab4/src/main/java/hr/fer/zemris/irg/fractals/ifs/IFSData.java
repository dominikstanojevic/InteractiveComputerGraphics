package hr.fer.zemris.irg.fractals.ifs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Dominik on 27.5.2017..
 */
public class IFSData {
    public int pointsNumber;
    public int limit;
    public int eta[];

    public Map<Transformation, Double> transformations = new LinkedHashMap<>();

    public static IFSData parse(Path path) {
        try (Scanner sc = new Scanner(path)) {
            IFSData data = new IFSData();

            data.pointsNumber = Integer.parseInt(preprocessLine(sc.nextLine()));
            data.limit = Integer.parseInt(preprocessLine(sc.nextLine()));

            String[] first = preprocessLine(sc.nextLine()).split("\\s+");
            String[] second = preprocessLine(sc.nextLine()).split("\\s+");

            data.eta =
                    Stream.concat(Arrays.stream(first), Arrays.stream(second)).mapToInt(s -> Integer.parseInt(s.trim()))
                            .toArray();

            while (sc.hasNextLine()) {
                String line = preprocessLine(sc.nextLine());
                if (line == null) {
                    continue;
                }

                addTransformation(data.transformations, line);
            }

            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void addTransformation(Map<Transformation, Double> transformations, String line) {
        String[] data = line.split("\\s+");
        if (data.length != 7) {
            throw new RuntimeException("Invalid length.");
        }

        Transformation transformation = new Transformation();
        transformation.xTransform =
                IntStream.of(0, 1, 4).mapToDouble(i -> Double.parseDouble(data[i].trim())).toArray();
        transformation.yTransform =
                IntStream.of(2, 3, 5).mapToDouble(i -> Double.parseDouble(data[i].trim())).toArray();

        transformations.put(transformation, Double.parseDouble(data[6]));
    }

    private static String preprocessLine(String line) {
        int index = line.indexOf("#");
        if (index == 0) {
            return null;
        }

        if (index != -1) {
            line = line.substring(0, index);
        }

        return line.trim();
    }

    public static class Transformation {
        public double[] xTransform;
        public double[] yTransform;
    }
}
