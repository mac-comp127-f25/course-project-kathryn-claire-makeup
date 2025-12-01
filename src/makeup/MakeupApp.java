package makeup;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.ui.Button;

public class MakeupApp {
    
    private CanvasWindow canvas;
    private final BrushSettingsView brushSettingsView;
    private Brush currentBrush;
    private GraphicsGroup paintLayer; 
    private final List<Brush> availableBrushes;
    private final List<Actions> buttons;
    private Actions currentButton;

    public MakeupApp() {
        canvas = new CanvasWindow("Makeup", 900, 800);
        canvas.add(new Marilyn().getGraphics(), -50, -60);

        paintLayer = new GraphicsGroup();
        canvas.add(paintLayer);

        brushSettingsView = new BrushSettingsView(Color.WHITE, 60);
        canvas.add(brushSettingsView, 10 - brushSettingsView.getBounds().getMinX(), 10);

        availableBrushes = List.of(new Blush(), new Bronzer(), new Eraser());
        currentBrush = availableBrushes.get(0);

        if (currentBrush.getClass() == Blush.class) {
            brushSettingsView.setColor(Color.PINK);
        } else if (currentBrush.getClass() == Bronzer.class) {
            brushSettingsView.setColor(Color.BLACK);
        }

        buttons = List.of(new ClearAll());
        currentButton = buttons.get(0);

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

        public void addButton(Actions button, double y) {
            String label = button.getClass().getSimpleName();
            Button buttonGraphic = new Button (label);
            buttonGraphic.setPosition (10,y);
            canvas.add(buttonGraphic);

            buttonGraphic.onClick(() -> {
                currentButton = button;
            });
        }

        public static void main(String[] args) {
            new MakeupApp();
        }
    
}
