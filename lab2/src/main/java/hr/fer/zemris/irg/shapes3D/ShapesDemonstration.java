package hr.fer.zemris.irg.shapes3D;

import hr.fer.zemris.irg.shapes3D.models.ObjectModel;
import hr.fer.zemris.irg.shapes3D.models.Vertex3D;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Dominik on 31.3.2017..
 */
public class ShapesDemonstration {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.err.println("Invalid number of arguments. Path to the .obj file expected.");
            System.exit(-1);
        }

        String path = args[0].trim();
        Scanner obj = new Scanner(Paths.get(path));
        ObjectModel model = ObjectModel.parse(obj);
        System.out.println("Object successfully loaded.");

        interact(model);
    }

    private static void interact(ObjectModel model) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            String line = sc.nextLine().trim();
            if(line.equals("quit")) {
                break;
            }

            if(line.startsWith("vertex ")) {
                Vertex3D vertex = Utils.parseVertex(line.replaceFirst("vertex\\s+", ""));
                ObjectModel.Position position = model.getVertexPosition(vertex);
                System.out.println(vertex + " is " + position.name().toLowerCase() + " the given object.");
            } else if(line.startsWith("normalize")) {
                model.normalize();
                System.out.println(model.dumpToOBJ());
            }
        }
    }
}
