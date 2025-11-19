package makeup;
import java.awt.Color;


public class BrushOptions {
/**
 * Describes the adjustable settings that can apply to a brush.
 */
    private Color color;
    private int radius;

    BrushOptions(Color color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }
    
}
