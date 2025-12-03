package makeup;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

public class ClearAll implements Brush {
        @Override
        public void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, Color color, float radius) {
        paintLayer.removeAll();
        GraphicsObject toClearAll = paintLayer.getElementAt(location.getX(), location.getY());
        if (toClearAll != null) {
            paintLayer.remove(toClearAll);
        }
        
    }
    
}
