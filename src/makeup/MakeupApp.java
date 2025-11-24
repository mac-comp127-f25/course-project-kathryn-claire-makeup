package makeup;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
// import makeup.SprayPaint.Brush;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.ui.Button;

public class MakeupApp {
    
    private CanvasWindow canvas;
    private final BrushSettingsView brushSettingsView;
    private Brush currentBrush;
    private GraphicsGroup paintLayer; 
    private final List<Brush> availableBrushes;

    public MakeupApp() {
        canvas = new CanvasWindow("Makeup", 900, 800);
        canvas.add(new MonaLisa().getGraphics(), -50, -60);

        paintLayer = new GraphicsGroup();
        canvas.add(paintLayer);

        brushSettingsView = new BrushSettingsView(Color.BLUE, 60);
        canvas.add(brushSettingsView, 10 - brushSettingsView.getBounds().getMinX(), 10);

        availableBrushes = List.of(new SprayBrush(), new Eraser());
        currentBrush = availableBrushes.get(0);

        canvas.onMouseDown(event -> sprayPaint(event.getPosition()));
        canvas.onDrag(event -> sprayPaint(event.getPosition()));


        double y = 300;
        for (Brush brush : availableBrushes) {
            addBrushButton(brush, y);
            y += 40;
        }

        }

        public void sprayPaint(Point location) {
            BrushOptions brushOptions = brushSettingsView.getBrushOptions();
            Color color = brushOptions.getColor();
            float radius = brushOptions.getRadius();
            this.currentBrush.apply(paintLayer, location, brushOptions, color, radius);

        }

        public void addBrushButton(Brush brush, double y) {
            String label = brush.getClass().getSimpleName();
            Button button = new Button(label);
            button.setPosition(10, y);
            canvas.add(button);

            button.onClick(() -> {
                currentBrush = brush;
            });
        }

        public static void main(String[] args) {
            new MakeupApp();
        }

    
}
