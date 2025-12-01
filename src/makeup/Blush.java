package makeup;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Ellipse;
import java.awt.Color;

public class Blush implements Brush {
        private BrushSettingsView brushSettingsView;

        @Override
        public void apply(GraphicsGroup paintLayer, Point location, BrushOptions options, Color color, float radius) {
            float alpha = 0.2f;
            Ellipse dot = BrushUtils.createFuzzyDot(color, radius, alpha);
            dot.setCenter(location.getX(), location.getY());
            
            brushSettingsView = new BrushSettingsView(color, 0);

            // updateColorFromField(0, brushSettingsView.getRField());
            // updateColorFromField(1, brushSettingsView.getGField());
            // updateColorFromField(2, brushSettingsView.getBField());

            paintLayer.add(dot);
        }

}
