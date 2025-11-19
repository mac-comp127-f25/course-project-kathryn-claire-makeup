package makeup;

// import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import java.awt.Color;


public class Brushes {
    public interface Brush {
        void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, Color color, float radius);
    }

    public static class Eraser implements Brush {
        @Override
        public void apply(GraphicsGroup paintLayer, Point position, BrushOptions options, Color color, float radius) {
        GraphicsObject toErase = paintLayer.getElementAt(position.getX(), position.getY());
        if (toErase != null) {
            paintLayer.remove(toErase);
        }
    }
    }
        
    public static class SprayBrush implements Brush{
        @Override
        public void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, Color color, float radius) {
            float alpha = 0.2f;
            
            Ellipse dot = BrushUtils.createFuzzyDot(color, radius, alpha);
            dot.setCenter(location.getX(), location.getY());
            paintLayer.add(dot);
        }

    }
}

