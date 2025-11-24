package makeup;
import edu.macalester.graphics.Image;


public class MonaLisa extends Face {
    @Override
    protected void buildGraphics(){
        Image monaLisa = new Image(0, 0, "monaLisa.jpg");
        getGraphics().add(monaLisa);
    }

}
