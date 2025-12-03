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
    private Button blushButton, bronzerButton, monaLisaButton, marilynButton;
    private GraphicsGroup faceLayer;
    private Face currentFace;

    public MakeupApp() {
        canvas = new CanvasWindow("Makeup", 900, 800); // creates canvas

        faceLayer = new GraphicsGroup();
        canvas.add(faceLayer);
        
        currentFace = new Marilyn();
        currentFace.buildGraphics();
        faceLayer.add(currentFace.getGraphics());

        monaLisaButton = new Button("Mona Lisa");
        monaLisaButton.setPosition(10,460);
        canvas.add(monaLisaButton);
        monaLisaButton.onClick(() -> switchFace(new MonaLisa()));

        marilynButton = new Button("Marilyn");
        marilynButton.setPosition(10,500);
        canvas.add(marilynButton);
        marilynButton.onClick(() -> switchFace(new Marilyn()));

        paintLayer = new GraphicsGroup(); //creates paint layer
        canvas.add(paintLayer);

        brushSettingsView = new BrushSettingsView(Color.WHITE, 60); //creates the color and size changer
        canvas.add(brushSettingsView, 10 - brushSettingsView.getBounds().getMinX(), 10);

        availableBrushes = List.of(new Blush(), new Bronzer(), new Eraser(), new ClearAll()); //creates the different brushes
        currentBrush = availableBrushes.get(0); 

        blushButton = new Button("Blush");
        blushButton.setPosition(10, 300);
        canvas.add(blushButton);
        Brush blush = new Blush();
        blushButton.onClick(() -> brushSettingsView.setColor(new Color(255, 0, 0, 5))); 
        blushButton.onClick(() -> currentBrush = blush);

        bronzerButton = new Button("Bronzer");
        bronzerButton.setPosition(10, 340);
        canvas.add(bronzerButton);
        Brush bronzer = new Bronzer();
        bronzerButton.onClick(() -> brushSettingsView.setColor(new Color(148, 115, 82, 255))); 
        bronzerButton.onClick(() -> currentBrush = bronzer);

        canvas.onMouseDown(event -> sprayPaint(event.getPosition())); // draws whichever brush is selected
        canvas.onDrag(event -> sprayPaint(event.getPosition()));

        double y = 380; // adds the avaiableBrushes to the canvas
        for (int i = 2; i < availableBrushes.size(); i++) {
            Brush brush = availableBrushes.get(i);
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

        private void switchFace (Face newFace) {
            faceLayer.removeAll();
            paintLayer.removeAll();
            currentFace = newFace;
            currentFace.buildGraphics();
            faceLayer.add(currentFace.getGraphics());
        }
    
}
