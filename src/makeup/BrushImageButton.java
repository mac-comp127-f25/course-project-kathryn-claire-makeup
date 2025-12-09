package makeup;

import java.awt.Color;
import edu.macalester.graphics.Image;

class BrushImageButton {
        Brush brush;
        Image image;
        Color color;
        int size;

        BrushImageButton(Brush brush, Image image, Color color, int size) {
            this.brush = brush;
            this.image = image;
            this.color = color;
            this.size = size;
        }
    }
