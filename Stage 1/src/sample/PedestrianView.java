package sample;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PedestrianView {
    private Pedestrian person;
    private Rectangle avatar;
    private final double SIZE = 7;
    public PedestrianView(Comuna comuna, Pedestrian p) {
        person = p;
        avatar = new Rectangle(SIZE, SIZE, Color.BLUE);
        avatar.setX(person.getX()-SIZE/2);
        avatar.setY(person.getY()-SIZE/2);
        comuna.getView().getChildren().add(avatar);
    }
    public void update(Pedestrian p) {
        avatar.setX(p.getX());
        avatar.setY(p.getY());
    }
}
