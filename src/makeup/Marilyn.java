package makeup;
import edu.macalester.graphics.Image;


public class Marilyn extends Face {
    @Override
    protected void buildGraphics(){
        Image marilyn = new Image(0, 0, "marilyn.webp");
        getGraphics().add(marilyn);
    }

}
