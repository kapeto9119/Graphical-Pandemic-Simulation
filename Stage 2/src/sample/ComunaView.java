package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComunaView extends Group {

    public ComunaView(Comuna c){
        Rectangle territoryView = new Rectangle(c.getWidth(), c.getHeight(), Color.WHITE);
        territoryView.setStroke(Color.BROWN);
        getChildren().add(territoryView);
        setFocusTraversable(false);
    }
    public void update(Pedestrian p){
        p.updateView();
    }
}
