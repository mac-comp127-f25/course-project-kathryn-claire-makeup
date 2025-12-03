package makeup;
import edu.macalester.graphics.Image;


public class Marilyn extends Face {
    @Override
    protected void buildGraphics(){
        Image marilyn = new Image(250, 150, "marilynCutout.png");
        marilyn.setScale(1.35,1.35);
        getGraphics().add(marilyn);
    }
}
