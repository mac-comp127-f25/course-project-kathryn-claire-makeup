package makeup;

import edu.macalester.graphics.GraphicsGroup;

public abstract class Face {
    private final GraphicsGroup graphics;

    public Face() {
        graphics = new GraphicsGroup(0, 0);
        buildGraphics();
    }

    /**
     * @return the underlying graphics component.
     */
    public GraphicsGroup getGraphics() {
        return graphics;
    }

    /**
     * Concrete classes must override this and use it to draw the shape.
     */
    protected abstract void buildGraphics();

    
}
