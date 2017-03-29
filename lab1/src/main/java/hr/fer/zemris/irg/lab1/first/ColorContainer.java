package hr.fer.zemris.irg.lab1.first;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 15.3.2017..
 */
public class ColorContainer {
    private static ColorContainer singleton;
    private static List<Color> colors = new ArrayList<>();
    static {
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
    }

    private ColorContainer(){
    }

    public static ColorContainer getInstance() {
        if(singleton == null) {
            singleton = new ColorContainer();
        }

        return singleton;
    }

    public int getColorCount() {
        return colors.size();
    }

    public Color getColor(int index) {
        if(index < 0 || index >= colors.size()) {
            throw new IndexOutOfBoundsException();
        }

        return colors.get(index);
    }
}
