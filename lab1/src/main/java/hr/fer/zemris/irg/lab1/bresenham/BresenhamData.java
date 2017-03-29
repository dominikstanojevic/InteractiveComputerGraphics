package hr.fer.zemris.irg.lab1.bresenham;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 17.3.2017..
 */
public class BresenhamData {
    public List<Line> lines = new ArrayList<>();
    public boolean control;
    public boolean cut;
    public Point temp;
}
