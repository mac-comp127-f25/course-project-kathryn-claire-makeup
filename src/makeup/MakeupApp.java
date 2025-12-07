package makeup;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

import java.awt.Color;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import edu.macalester.graphics.ui.Button;

public class MakeupApp {
    
    private CanvasWindow canvas;
    private final BrushSettingsView brushSettingsView;
    private Brush currentBrush;
    private GraphicsGroup paintLayer; 
    private final List<Brush> availableBrushes;
    private Button blendButton, blushButton, bronzerButton, eyeShadowButton, monaLisaButton, marilynButton, fridaKahloButton;
    private GraphicsGroup faceLayer;
    private Face currentFace;

    public MakeupApp() {
        canvas = new CanvasWindow("Makeup", 900, 800); // creates canvas
        canvas.setBackground(Color.LIGHT_GRAY);

        faceLayer = new GraphicsGroup();
        canvas.add(faceLayer);

        GraphicsText makeupTitle = new GraphicsText("Makeup App!");
        makeupTitle.setPosition(250, 100);
        makeupTitle.setFont(FontStyle.BOLD, 75);
        canvas.add(makeupTitle);
        
        currentFace = new Marilyn();
        currentFace.buildGraphics();
        faceLayer.add(currentFace.getGraphics());

        monaLisaButton = new Button("Mona Lisa");
        monaLisaButton.setPosition(45,700);
        canvas.add(monaLisaButton);
        monaLisaButton.onClick(() -> switchFace(new MonaLisa()));

        marilynButton = new Button("Marilyn");
        marilynButton.setPosition(45,620);
        canvas.add(marilynButton);
        marilynButton.onClick(() -> switchFace(new Marilyn()));

        fridaKahloButton = new Button("Frida Kahlo");
        fridaKahloButton.setPosition(45,660);
        canvas.add(fridaKahloButton);
        fridaKahloButton.onClick(() -> switchFace(new FridaKahlo()));

        paintLayer = new GraphicsGroup(); //creates paint layer
        canvas.add(paintLayer);

        brushSettingsView = new BrushSettingsView(Color.WHITE, 60); //creates the color and size changer
        canvas.add(brushSettingsView, 10 - brushSettingsView.getBounds().getMinX(), 10);

        availableBrushes = List.of(new Blush(), new Bronzer(), new Eraser(), new ClearAll()); //creates the different brushes
        currentBrush = availableBrushes.get(0); 

        // blushButton = new Button("Blush");
        // blushButton.setPosition(45, 300);
        // canvas.add(blushButton);
        Brush blush = new Blush();
        // blushButton.onClick(() -> brushSettingsView.setColor(new Color(200, 0, 0, 5))); 
        // blushButton.onClick(() -> brushSettingsView.updateBrushSizeFromField(50));
        // blushButton.onClick(() -> currentBrush = blush);

        Image blushImageButton = new Image(45, 300, "blush.png");
        blushImageButton.setPosition(35, 300);
        canvas.add(blushImageButton);

        canvas.onMouseDown(event -> {
            Point pos = event.getPosition();
            if (blushImageButton.testHit(pos.getX(), pos.getY())) {
                brushSettingsView.setColor(new Color(200, 0, 0, 5));
                brushSettingsView.updateBrushSizeFromField(50);
                currentBrush = blush;
            }
            sprayPaint(event.getPosition());
        });

        bronzerButton = new Button("Bronzer");
        bronzerButton.setPosition(45, 420);
        canvas.add(bronzerButton);
        Brush bronzer = new Bronzer();
        bronzerButton.onClick(() -> brushSettingsView.setColor(new Color(148, 115, 82, 255))); 
        bronzerButton.onClick(() -> brushSettingsView.updateBrushSizeFromField(40));
        bronzerButton.onClick(() -> currentBrush = bronzer);

        eyeShadowButton = new Button("Eye Shadow");
        eyeShadowButton.setPosition(45, 460);
        canvas.add(eyeShadowButton);
        Brush eyeShadow = new EyeShadow();
        eyeShadowButton.onClick(() -> brushSettingsView.setColor(new Color(100, 0, 200, 150))); 
        eyeShadowButton.onClick(() -> brushSettingsView.updateBrushSizeFromField(15));
        eyeShadowButton.onClick(() -> currentBrush = eyeShadow);

        blendButton = new Button("Blend");
        blendButton.setPosition(45, 500);
        canvas.add(blendButton);
        Brush blend = new Blend();
        blendButton.onClick(() -> brushSettingsView.setColor(new Color(192, 192, 192, 50))); 
        blendButton.onClick(() -> brushSettingsView.updateBrushSizeFromField(70));
        blendButton.onClick(() -> currentBrush = blend);

        // canvas.onMouseDown(event -> sprayPaint(event.getPosition())); // draws whichever brush is selected
        // canvas.onDrag(event -> sprayPaint(event.getPosition()));

        double y = 540; // adds the avaiableBrushes to the canvas
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
            button.setPosition(45, y);
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
