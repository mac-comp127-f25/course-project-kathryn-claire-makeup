package makeup;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class MonaLisaButton implements Actions{
    @Override
        public void apply(GraphicsGroup paintLayer, Point position, BrushOptions options, float radius) {
        new Marilyn();
        }
}
