package makeup;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public interface Actions {
    void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, float radius);

}
