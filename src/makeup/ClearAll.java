package makeup;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import java.awt.Color;

public class ClearAll implements Brush {
        @Override
        public void apply(GraphicsGroup paintLayer, Point position, BrushOptions options, Color color, float radius) {
        paintLayer.removeAll();
        
    }
    
}
