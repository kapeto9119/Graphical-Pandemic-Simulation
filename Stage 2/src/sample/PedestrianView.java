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
    private Rectangle avatarRec;
    public final double SIZE = 5;

    public PedestrianView(Comuna comuna, Pedestrian p) {
        this.person = p;
        this.comuna = comuna;
        if (p.getStatus().equals("susceptible")){
            avatarInf = null;
            avatarRec = null;
            avatarSusc = createSusc();
            comuna.getView().getChildren().add(avatarSusc);
        }
        if (p.getStatus().equals("infectado")){
            avatarSusc = null;
            avatarRec = null;
            avatarInf = createInf();
            comuna.getView().getChildren().add(avatarInf);
        }
    }

    public Rectangle createSusc(){
        Rectangle avatar;
        avatar = new Rectangle(SIZE, SIZE, Color.BLUE);
        avatar.setX(person.getX()-SIZE/2);
        avatar.setY(person.getY()-SIZE/2);
        return avatar;
    }

    public Circle createInf(){
        Circle avatar;
        avatar = new Circle(SIZE, Color.RED);
        avatar.setCenterX(person.getX()-SIZE/2);
        avatar.setCenterY(person.getY()-SIZE/2);
        return avatar;
    }

    public Rectangle createRec(){
        Rectangle avatar;
        avatar = new Rectangle(SIZE + 3, SIZE + 3, Color.BROWN);
        avatar.setX(person.getX() - SIZE/2);
        avatar.setY(person.getY() + SIZE/2);
        return avatar;
    }

    public void update(Pedestrian p) {
        if (p.getStatus().equals("susceptible")){
            avatarSusc.setX(p.getX());
            avatarSusc.setY(p.getY());
        }
        if (p.getStatus().equals("infectado")){
            avatarInf.setCenterY(p.getX());
            avatarInf.setCenterY(p.getY());
        }
        if (p.getStatus().equals("recuperado")){
            avatarRec.setX(p.getX());
            avatarRec.setY(p.getY());
        }
    }

    public void setForm(Pedestrian pedestrian, String _newstatus) {
        if (pedestrian.getStatus().equals("susceptible")){
            if (_newstatus.equals("infectado")) {
                comuna.getView().getChildren().remove(avatarSusc);
                this.avatarSusc = null;
                this.avatarInf = createInf();
                comuna.getView().getChildren().add(avatarInf);
            }
        }
        if (pedestrian.getStatus().equals("infectado")){
            if (_newstatus.equals("recuperado")){
                comuna.getView().getChildren().remove(avatarInf);
                this.avatarInf = null;
                this.avatarRec = createRec();
                comuna.getView().getChildren().add(avatarRec);
            }
        }
    }
}
