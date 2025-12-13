package makeup;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Ellipse;
import java.awt.Color;

public class Blend implements Brush {
    @Override
    public void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, Color color, float radius) {
        Color blend = new Color(255,255,255,5);
        float alpha = 0.05f;
        Ellipse dot = BrushUtils.createFuzzyDot(blend, radius, alpha);
        dot.setCenter(location.getX(), location.getY());
        paintLayer.add(dot);
    }   
}
