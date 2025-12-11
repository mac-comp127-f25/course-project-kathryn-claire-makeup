package makeup;

import java.awt.Color;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

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

        public Point getClick(Point click) {
            double x = click.getX() - image.getX();
            double y = click.getY() - image.getY();
            return new Point(x,y);
        }
    }
