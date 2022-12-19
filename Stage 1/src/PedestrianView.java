import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PedestrianView {
    /*
    Variables creadas en esta clase, privadas de tipo Pedestrian y Rectangle
     */
    private Pedestrian person;
    private Rectangle avatar;
    private final double SIZE = 7;

    /**
     * @method PedestrianView : realiza las configuraciones para la vista, tal como crear
     * formas de determinada SIZE y settear y obtener las cordenadas de un objeto de tipo persona
     * @param Comuna comuna : se requiere como argumento un objeto de tipo Comuna
     * @param Pedestrian p: se requiere como argumento un objeto de tipo pedestrian
     */
    public PedestrianView(Comuna comuna, Pedestrian p) {
        person = p;
        avatar = new Rectangle(SIZE, SIZE, Color.BLUE);
        avatar.setX(person.getX()-SIZE/2);
        avatar.setY(person.getY()-SIZE/2);
        comuna.getView().getChildren().add(avatar);
    }
    /**
     * @method update(Pedestrian p): realiza la actualizacion de cordenadas
     * @param Pedestrian p: se requiere como argumento un objeto de tipo pedestrian
     */
    public void update(Pedestrian p) {
        avatar.setX(p.getX());
        avatar.setY(p.getY());
    }
}
