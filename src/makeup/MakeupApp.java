package makeup;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<BrushImageButton> brushIcons;
    private double minX = 300;
    private double minY = 100;
    private double maxX = 700;
    private double maxY = 700;
    private Map<Class<? extends Face>, GraphicsGroup> faceLayers = new HashMap<>();

    public MakeupApp() {
        //creates canvas
        canvas = new CanvasWindow("Makeup App!", 900, 800);
        canvas.setBackground(Color.LIGHT_GRAY);

        //creates the face layer
        faceLayer = new GraphicsGroup();
        canvas.add(faceLayer);

        //creates the title of the app on the screen
        GraphicsText makeupTitle = new GraphicsText("Makeup App!");
        makeupTitle.setPosition(250, 100);
        makeupTitle.setFont(FontStyle.BOLD, 75);
        canvas.add(makeupTitle);
        
        //sets marilyn as the current face at the beginning of the game
        currentFace = new Marilyn();
        currentFace.buildGraphics();
        faceLayer.add(currentFace.getGraphics());

        //adds the buttons for the different faces to change between
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

        //creates the paint layer the user will actually draw on
        paintLayer = new GraphicsGroup();
        canvas.add(paintLayer);

        //creates brush settings view 
        brushSettingsView = new BrushSettingsView(Color.WHITE, 60);

        //creates a list of the non makeup brushes, creating the brushes, and setting the default start brush
        availableBrushes = List.of(new Eraser(), new ClearAll());
        currentBrush = availableBrushes.get(0); 

        //calls the method to set up the actual brush selection on the screen
        setupBrushSelection();

        //runs when a brush picture is clicked on screen, changes the current brush to the one selected
        canvas.onMouseDown(event -> {
            Point position = event.getPosition();
            for (BrushImageButton icon : brushIcons) {
                if (icon.image.testHit(position.getX(), position.getY())) { 
                    //if the brush is eyeshadow, then it calls adjustEyeshadow to adjust the color based on the click point
                    if (icon.brush instanceof EyeShadow) {
                        adjustEyeshadow(icon, position);
                        return;
                    }
                    brushSettingsView.setColor(icon.color);
                    brushSettingsView.updateBrushSizeFromField(icon.size);
                    currentBrush = icon.brush;
                    return;
                }
            }
            if (isWithinBounds(position)) {
                sprayPaint(position);
            }
        });

        //creates the blend button
        blendButton = new Button("Blend");
        blendButton.setPosition(45, 500);
        canvas.add(blendButton);
        Brush blend = new Blend();
        blendButton.onClick(() -> brushSettingsView.updateBrushSizeFromField(70));
        blendButton.onClick(() -> currentBrush = blend);

        //adds the available brushes to the canvas, skips the 
        double y = 540; // adds the avaiableBrushes to the canvas
        for (int i = 0; i < availableBrushes.size(); i++) {
            Brush brush = availableBrushes.get(i);
            addBrushButton(brush, y);
            y += 40;
        }
    }

    /** 
     * Applies the paint to the canvas
     * @param location is the location the user clicked on the screen, where the spray paint will show up
    */
    public void sprayPaint(Point location) {
        BrushOptions brushOptions = brushSettingsView.getBrushOptions();
        Color color = brushOptions.getColor();
        float radius = brushOptions.getRadius();
        this.currentBrush.apply(paintLayer, location, brushOptions, color, radius);
    }

    /**
     * Adds the brush button for the eraser and clear all
     * @param brush is the brush that has been selected
     * @param y is the y position the button will be placed at
     */
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

    /**
     * Changes the face the user is putting makeup on
     * @param newFace is the face the user has selected
     */
    private void switchFace (Face newFace) {
        Class<? extends Face> newFaceType = newFace.getClass();

        if (currentFace.getClass() == newFace.getClass()) {
            return;
        } 

        faceLayers.put(currentFace.getClass(), paintLayer);

        faceLayer.removeAll();
        canvas.remove(paintLayer);

        currentFace = newFace;
        currentFace.buildGraphics();
        faceLayer.add(currentFace.getGraphics());

        paintLayer = faceLayers.getOrDefault(newFaceType, new GraphicsGroup());
        faceLayers.putIfAbsent(newFaceType, paintLayer);

        canvas.add(paintLayer);
    }
    

    /**
     * Sets up the brush selection of the actual makeup brushes
     */
    private void setupBrushSelection() {
        brushIcons = List.of(
        new BrushImageButton(new Blush(), new Image(0,0,"blush.png"), new Color(200, 75, 75, 1), 45),
        new BrushImageButton(new Bronzer(), new Image(0,0,"bronzer.png"), new Color(148, 115, 82, 255), 35),
        new BrushImageButton(new EyeShadow(), new Image(0,0,"eyeshadow.png"), new Color(100, 0, 200, 150), 10)
        );
        loadBrushIcons();
    }

    /**
     * Puts the pictures of the makeup on the screen
     */
    private void loadBrushIcons() {
        double yOffset = -125;
        for (BrushImageButton icon : brushIcons) {
            if (icon.brush instanceof EyeShadow) {
                icon.image.setPosition(-150, yOffset + 20);
            } else {
                icon.image.setPosition(-160, yOffset);
            }
            if (icon.brush instanceof Bronzer) {
                icon.image.setScale(0.42);
            } else {
                icon.image.setScale(0.25);
            }
            canvas.add(icon.image);
            yOffset += 150;
        }
    }

    /**
     * Checks that the position the user wants to put makeup on is within the bounds of the face
     * @param position where the user clicked on the screen
     * @return true if it is within the bounds, false if it is out of the bounds
     */
    private boolean isWithinBounds(Point position) {
        double positionX = position.getX();
        double positionY = position.getY();
        return (positionX >= minX && positionX <= maxX) && (positionY >= minY && positionY <= maxY); 
    }

    /**
     * Adjusts the color when the brush is an eyeshadow, color based on where they click on the icon
     * @param icon is the eyeshadow brush 
     * @param click is the location they clicked the icon
     */
    private void adjustEyeshadow (BrushImageButton icon, Point click) {
        Color newColor;
        double x = click.getX() - icon.image.getX();
        double y = click.getY() - icon.image.getY();
        double width = icon.image.getWidth();
        double height = icon.image.getHeight();
        boolean left = x < (width/2);
        boolean top = y < (height/2);
        if (top && left) {
            newColor = new Color (63, 153, 140);
        } else if (top && !left) {
            newColor = new Color (254,107,104);
        } else if (!top && left) {
            newColor = new Color (255,207,86);
        } else {
            newColor = new Color (232,106,176);
        }
        brushSettingsView.setColor(newColor);
        brushSettingsView.updateBrushSizeFromField(10);
        currentBrush = icon.brush;
    }
}
