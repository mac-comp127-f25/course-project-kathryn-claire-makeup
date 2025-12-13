package makeup;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Ellipse;
import java.awt.Color;

public class Bronzer implements Brush {
        @Override
        public void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, Color color, float radius) {
            float alpha = 0.2f;
            Ellipse dot = BrushUtils.createFuzzyDot(color, radius, alpha);
            dot.setCenter(location.getX(), location.getY());
            paintLayer.add(dot);
        }

}