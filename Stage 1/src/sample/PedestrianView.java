package sample;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PedestrianView {
    private Pedestrian person;
    private Rectangle avatar;
    private final double SIZE = 5;
    public PedestrianView(Comuna comuna, Pedestrian p) {
        person = p;
        avatar = new Rectangle(SIZE, SIZE, Color.BLUE);
        avatar.setX(person.getX()-SIZE/2);   // Rectangle x position is the X coordinate of the
        // upper-left corner of the rectangle
        avatar.setY(person.getY()-SIZE/2); // Rectangle y position is the Y coordinate of the
        // upper-left corner of the rectangle
        comuna.getView().getChildren().add(avatar);
    }
    public void update(Pedestrian p) {
        avatar.setX(p.getX());
        avatar.setY(p.getY());
    }
}
