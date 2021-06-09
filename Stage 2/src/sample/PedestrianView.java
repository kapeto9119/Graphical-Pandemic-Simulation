package sample;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class PedestrianView {
    private Pedestrian person;
    private Comuna comuna;
    private Rectangle avatarSusc;
    private Circle avatarInf;
    private Polygon avatarRec;
    private final double SIZE = 5;

    public PedestrianView(Comuna comuna, Pedestrian p) {
        this.person = p;
        this.comuna = comuna;
        if (p.getStatus() == "susceptible"){
            avatarInf = null;
            avatarRec = null;
            avatarSusc = createSusc();
            comuna.getView().getChildren().add(avatarSusc);
        }
        if (p.getStatus() == "infectado"){
            avatarSusc = null;
            avatarRec = null;
            avatarInf = createInf();
            comuna.getView().getChildren().add(avatarInf);
        }
    }

    public Rectangle createSusc(){
        Rectangle avatar;
        avatar = new Rectangle(SIZE, SIZE, Color.BLUE);
        avatar.setX(person.getX()-SIZE/2);   // Rectangle x position is the X coordinate of the
        // upper-left corner of the rectangle
        avatar.setY(person.getY()-SIZE/2); // Rectangle y position is the Y coordinate of the
        // upper-left corner of the rectangle
        return avatar;
    }

    public Circle createInf(){
        Circle avatar;
        avatar = new Circle(SIZE, Color.RED);
        avatar.setCenterX(person.getX()-SIZE/2);
        avatar.setCenterY(person.getY()-SIZE/2);
        return avatar;
    }

    public Polygon createRec(){
        Polygon avatar;
        avatar = new Polygon(
            0d, 0d,
            (SIZE * Math.tan(180)), -SIZE,
            -(SIZE * Math.tan(180)), -SIZE
        );
        avatar.setLayoutX(person.getX());
        avatar.setLayoutY(person.getY());
        avatar.setFill(Color.GREEN);
        return avatar;
    }

    public void update(Pedestrian p) {
        if (p.getStatus() == "susceptible"){
            avatarSusc.setX(p.getX());
            avatarSusc.setY(p.getY());
        }
        if (p.getStatus() == "infectado"){
            avatarInf.setCenterY(p.getX());
            avatarInf.setCenterY(p.getY());
        }
        if (p.getStatus() == "recuperado"){
            avatarRec.setLayoutX(p.getX());
            avatarRec.setLayoutY(p.getY());
        }
    }

    public void setForm(Pedestrian pedestrian, String _newstatus) {
        if (pedestrian.getStatus() == "susceptible"){
            if (_newstatus == "infectado") {
                comuna.getView().getChildren().remove(avatarSusc);
                this.avatarSusc = null;
                this.avatarInf = createInf();
                comuna.getView().getChildren().add(avatarInf);
            }
        }
        if (pedestrian.getStatus() == "infectado"){
            if (_newstatus == "recuperado"){
                comuna.getView().getChildren().remove(avatarInf);
                this.avatarInf = null;
                this.avatarRec = createRec();
                comuna.getView().getChildren().add(avatarRec);
            }
        }
    }
}
