package makeup;
import edu.macalester.graphics.Image;


public class FridaKahlo extends Face {
    @Override
    protected void buildGraphics(){
        Image marilyn = new Image(325, 230, "fridaKahloCutout.gif");
        marilyn.setScale(1.6, 1.6);
        getGraphics().add(marilyn);
    }
}