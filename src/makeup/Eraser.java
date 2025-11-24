package makeup;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import java.awt.Color;

public class Eraser implements Brush {
        @Override
        public void apply(GraphicsGroup paintLayer, Point position, BrushOptions options, Color color, float radius) {
        GraphicsObject toErase = paintLayer.getElementAt(position.getX(), position.getY());
        if (toErase != null) {
            paintLayer.remove(toErase);
        }
    }
    
}
