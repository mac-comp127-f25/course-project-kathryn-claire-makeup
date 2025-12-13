package makeup;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

import java.awt.Color;

public interface Brush {
        void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, Color color, float radius);
}

