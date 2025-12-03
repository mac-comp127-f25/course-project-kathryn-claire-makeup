package makeup;
import edu.macalester.graphics.Image;


public class MonaLisa extends Face {
    @Override
    protected void buildGraphics(){
        Image monaLisa = new Image(10, -100, "monaLisaCutout.png");
        monaLisa.setScale(0.75);
        getGraphics().add(monaLisa);
    }
}
