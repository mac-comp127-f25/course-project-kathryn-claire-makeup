package makeup;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
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
    private Button blendButton, monaLisaButton, marilynButton, fridaKahloButton;
    private GraphicsGroup faceLayer;
    private Face currentFace;

    public MakeupApp() {
        canvas = new CanvasWindow("Makeup App!", 900, 800); // creates canvas
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

        marilynButton = new Button("Marilyn Monroe");
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

        availableBrushes = List.of(new Blush(), new Bronzer(), new Eraser(), new ClearAll()); //creates the different brushes
        currentBrush = availableBrushes.get(0); 

        Brush blush = new Blush();
        Image blushImageButton = new Image(45, 300, "blush.png");
        blushImageButton.setPosition(-160, -125);
        blushImageButton.setScale(0.25);
        canvas.add(blushImageButton);

        canvas.onMouseDown(event -> {
            Point position = event.getPosition();
            if (blushImageButton.testHit(position.getX(), position.getY())) {
                brushSettingsView.setColor(new Color(200, 75, 75, 1));
                brushSettingsView.updateBrushSizeFromField(45);
                currentBrush = blush;
            }
            sprayPaint(event.getPosition());
        });

        Brush bronzer = new Bronzer();
        Image bronzerImageButton = new Image(45, 300, "bronzer.png");
        bronzerImageButton.setPosition(-160,25);
        bronzerImageButton.setScale(0.40);
        canvas.add(bronzerImageButton);

        canvas.onMouseDown(event -> {
            Point position = event.getPosition();
            if (bronzerImageButton.testHit(position.getX(), position.getY())) {
                brushSettingsView.setColor(new Color(148, 115, 82, 255));
                brushSettingsView.updateBrushSizeFromField(35);
                currentBrush = bronzer;
            }
            sprayPaint(event.getPosition());
        });

        Brush eyeShadow = new EyeShadow();
        Image eyeShadowImageButton = new Image(45, 300, "eyeshadow.png");
        eyeShadowImageButton.setPosition(-155,160);
        eyeShadowImageButton.setScale(0.25);
        canvas.add(eyeShadowImageButton);

        canvas.onMouseDown(event -> {
            Point position = event.getPosition();
            if (eyeShadowImageButton.testHit(position.getX(), position.getY())) {
                brushSettingsView.setColor(new Color(100, 0, 200, 150));
                brushSettingsView.updateBrushSizeFromField(10);
                currentBrush = eyeShadow;
            }
            sprayPaint(event.getPosition());
        });

        blendButton = new Button("Blend");
        blendButton.setPosition(45, 500);
        canvas.add(blendButton);
        Brush blend = new Blend();
        blendButton.onClick(() -> brushSettingsView.setColor(new Color(192, 192, 192, 50))); 
        blendButton.onClick(() -> brushSettingsView.updateBrushSizeFromField(70));
        blendButton.onClick(() -> currentBrush = blend);

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
