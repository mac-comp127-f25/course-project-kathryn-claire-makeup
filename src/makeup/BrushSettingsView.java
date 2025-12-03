package makeup;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

import java.awt.Color;
import java.util.function.Consumer;

public class BrushSettingsView extends GraphicsGroup {
    private Color color;
    private int radius;

    private Rectangle colorDisplay;
    private TextField redField, greenField, blueField, sizeField, alphaField;
    private Button blushButton, bronzerButton;

    public BrushSettingsView(Color initialColor, int initialSize) {
        colorDisplay = new Rectangle(0, 0, 100, 100);
        add(colorDisplay);

        redField = addComponentField("Red", colorDisplay, 8);
        greenField = addComponentField("Green", redField, 4);
        blueField = addComponentField("Blue", greenField, 4);
        alphaField = addComponentField("Alpha", blueField, 4);

        redField.onChange((text) -> updateColorFromField(0, redField));
        greenField.onChange((text) -> updateColorFromField(1, greenField));
        blueField.onChange((text) -> updateColorFromField(2, blueField));
        alphaField.onChange((text) -> updateColorFromField(3, alphaField));

        sizeField = addComponentField("Size", alphaField, 16);
        sizeField.onChange((text) -> updateBrushSizeFromField());
        sizeField.setText(String.valueOf(initialSize));

        blushButton = new Button("Blush");
        blushButton.setPosition(100, 300);
        add(blushButton);
        blushButton.onClick(() -> setColor(new Color(255, 0, 0, 255))); 

        bronzerButton = new Button("Bronzer");
        bronzerButton.setPosition(100, 400);
        add(bronzerButton);
        bronzerButton.onClick(() -> setColor(new Color(148, 115, 82, 255))); 

        setColor(initialColor);
        radius = initialSize;
    }

    private TextField addComponentField(String label, GraphicsObject positionAfter, int margin) {
        double y = positionAfter.getBoundsInParent().getMaxY() + margin;

        GraphicsText labelGraphics = new GraphicsText(label);
        labelGraphics.setPosition(-labelGraphics.getWidth() - 5, y);
        add(labelGraphics);

        TextField field = new TextField();
        field.setPosition(0, y);
        add(field);

        labelGraphics.setCenter(labelGraphics.getCenter().getX(), field.getCenter().getY());
        return field;
    }

    public BrushOptions getBrushOptions() {
        return new BrushOptions(color, radius);
    }

    public void setColor(Color color) {
        setColorWithoutFields(color);
        updateComponentField(redField,   color.getRed());
        updateComponentField(greenField, color.getGreen());
        updateComponentField(blueField,  color.getBlue());
        updateComponentField(alphaField, color.getAlpha());
    }

    private void updateComponentField(TextField field, int value) {
        field.setText(String.valueOf(value));
    }

    private void setColorWithoutFields(Color color) {
        this.color = color;
        colorDisplay.setFillColor(color);
    }

    private void updateColorFromField(int index, TextField field) {
        float[] components = color.getRGBColorComponents(null);
        readIntField(field, (newValue) -> {
            components[index] = Math.max(0, Math.min(1, newValue / 255.0f));
            setColorWithoutFields(new Color(components[0], components[1], components[2]));
        });
    }

    private void updateBrushSizeFromField() {
        readIntField(sizeField, (newSize) ->
            radius = Math.max(0, newSize));
    }

    private void readIntField(TextField field, Consumer<Integer> updateAction) {
        try {
            updateAction.accept(
                Integer.parseInt(
                    field.getText()));
            field.setBackground(Color.WHITE);
        } catch (NumberFormatException e) {
            field.setBackground(new Color(0xFFCCCC));
        }
    }

    public TextField getRField() {
        return redField;
    }

    public TextField getGField() {
        return greenField;
    }

    public TextField getBField() {
        return blueField;
    }

    public TextField getAField() {
        return alphaField;
    }
}



