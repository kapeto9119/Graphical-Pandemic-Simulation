
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComunaView extends Group {
    /**
     * @method ComunaView : realiza la entrega de un rectangulo como un territorio
     * para mostrarlo por pantalla, conviertendose en la comuna.
     * @param Comuna c : se requiere como argumento un objeto de tipo Comuna
     */
    public ComunaView(Comuna c){
        Rectangle territoryView = new Rectangle(c.getWidth(), c.getHeight(), Color.WHITE);
        territoryView.setStroke(Color.BROWN);
        getChildren().add(territoryView);
        setFocusTraversable(false);
    }
    /**
     * @method ComunaView : realiza la actualizacion del objeto p de tipo
     * pedestrian.
     * @param Pedestrian p : se requiere como argumento un objeto de tipo pedestrian
     */
    public void update(Pedestrian p){
        p.updateView();
    }
}
