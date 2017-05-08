package hr.fer.zemris.irg.projections;

import hr.fer.zemris.irg.shapes3D.models.ObjectModel;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Dominik on 7.5.2017..
 */
public class Utils {
    public static ObjectModel preprocessModel(String path) throws IOException {
        Scanner obj = new Scanner(Paths.get(path));
        ObjectModel model = ObjectModel.parse(obj);
        model.normalize();
        return model;
    }
}
