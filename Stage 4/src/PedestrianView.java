import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PedestrianView {
    /*
Variables creadas en esta clase, privadas para usarlas en la clase.
*/
    private final Pedestrian person;
    private final Comuna comuna;
    private Rectangle avatarSusc;
    private Rectangle suscBorder;
    private Circle avatarInf;
    private Circle infBorder;
    private Rectangle avatarRec;
    private Polygon avatarVac;
    public final double SIZE = 7;
    /**
     * @method PedestrianView : realiza las configuraciones para la vista, tal como crear
     * formas de determinada SIZE y settear y obtener las cordenadas de un objeto de tipo persona
     * @param Comuna comuna : se requiere como argumento un objeto de tipo Comuna
     * @param Pedestrian p: se requiere como argumento un objeto de tipo pedestrian
     */
    public PedestrianView(Comuna comuna, Pedestrian p) {
        this.person = p;
        this.comuna = comuna;
        if (p.getStatus().equals("susceptible")){
            if (p.checkMask()){
                avatarSusc = createSusc();
                suscBorder = createSuscBorder();
                comuna.getView().getChildren().addAll(suscBorder, avatarSusc);
            }else {
                avatarSusc = createSusc();
                comuna.getView().getChildren().add(avatarSusc);
            }
        }
        if (p.getStatus().equals("infectado")){
            if (p.checkMask()){
                avatarInf = createInf();
                infBorder = createInfBorderMask();
                comuna.getView().getChildren().addAll(infBorder, avatarInf);
            }else {
                avatarInf = createInf();
                infBorder = createInfBorderNoMask();
                comuna.getView().getChildren().addAll(infBorder, avatarInf);
            }
        }
    }
    /*
    creacion de una forma rectangular para su uso dependiendo del estado de infeccion
    */
    public Rectangle createSuscBorder(){
        Rectangle border;
        border = new Rectangle(SIZE + 3, SIZE + 3, Color.BLACK);
        border.setX(person.getX() - (SIZE / 2) - (double)(3/2));
        border.setY(person.getY() + (SIZE / 2) - (double)(3/2));
        return border;
    }
    /*
    creacion de una forma rectangular para su uso
    */
    public Rectangle createSusc(){
        Rectangle avatar;
        avatar = new Rectangle(SIZE, SIZE, Color.BLUE);
        avatar.setX(person.getX() - SIZE/2);
        avatar.setY(person.getY() + SIZE/2);
        return avatar;
    }
    /*
    creacion de una forma rectangular para su uso
    */
    public Rectangle createRec(){
        Rectangle avatar;
        avatar = new Rectangle(SIZE + 3, SIZE + 3, Color.BROWN);
        avatar.setX(person.getX() - SIZE/2);
        avatar.setY(person.getY() + SIZE/2);
        return avatar;
    }
    /*
    creacion de una forma Circular para su uso
    */
    public Circle createInfBorderMask(){
        Circle border;
        border = new Circle(SIZE + 3, Color.BLACK);
        border.setCenterX(person.getX());
        border.setCenterY(person.getY());
        return border;
    }
    /*
    creacion de una forma Circular para su uso
    */
    public Circle createInfBorderNoMask(){
        Circle border;
        border = new Circle(SIZE + 3, Color.DARKRED);
        border.setCenterX(person.getX());
        border.setCenterY(person.getY());
        return border;
    }
    /*
    creacion de una forma Circular para su uso
    */
    public Circle createInf(){
        Circle avatar;
        avatar = new Circle(SIZE, Color.RED);
        avatar.setCenterX(person.getX());
        avatar.setCenterY(person.getY());
        return avatar;
    }
    /*
    creacion de una forma Polygon para su uso
    */
    public Polygon createVac(){
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
    /**
     * @method update(Pedestrian p): realiza la actualizacion de cordenadas dependiendo del estado de infeccion
     * @param Pedestrian p: se requiere como argumento un objeto de tipo pedestrian
     */
    public void update(Pedestrian p) {
        if (p.getStatus().equals("susceptible")){
            if (p.checkMask()){
                avatarSusc.setX(p.getX());
                avatarSusc.setY(p.getY());
                suscBorder.setX(p.getX());
                suscBorder.setY(p.getY());
            }else {
                avatarSusc.setX(p.getX());
                avatarSusc.setY(p.getY());
            }
        }
        if (p.getStatus().equals("infectado")){
            if (p.checkMask()){
                avatarInf.setCenterX(p.getX());
                avatarInf.setCenterY(p.getY());
                infBorder.setCenterX(p.getX());
                infBorder.setCenterY(p.getY());
            }else {
                avatarInf.setCenterX(p.getX());
                avatarInf.setCenterY(p.getY());
                infBorder.setCenterX(p.getX());
                infBorder.setCenterY(p.getY());
            }
        }
        if (p.getStatus().equals("recuperado")){
            avatarRec.setX(p.getX());
            avatarRec.setY(p.getY());
        }
        if (p.getStatus().equals("vacunado")){
            avatarVac.setLayoutX(p.getX());
            avatarVac.setLayoutY(p.getY());
        }
    }
    /**
     * @method setForm: realiza el setter de una forma en especifica dependiendo del estado
     * de infeccion de un invididuo
     * @param Pedestrian p: se requiere como argumento un objeto de tipo pedestrian
     * @param String _newstatus: se requiere como argumento para realizar la comprobacion del estado
     */
    public void setForm(Pedestrian pedestrian, String _newstatus) {
        if (pedestrian.getStatus().equals("susceptible")){
            if (_newstatus.equals("infectado")) {
                if (pedestrian.checkMask()){
                    comuna.getView().getChildren().removeAll(avatarSusc, suscBorder);
                    this.avatarSusc = null;
                    this.suscBorder = null;
                    avatarInf = createInf();
                    infBorder = createInfBorderMask();
                }else{
                    comuna.getView().getChildren().remove(avatarSusc);
                    this.avatarSusc = null;
                    avatarInf = createInf();
                    infBorder = createInfBorderNoMask();
                }
                comuna.getView().getChildren().addAll(infBorder, avatarInf);
            }
        }
        if (pedestrian.getStatus().equals("infectado")){
            if (_newstatus.equals("recuperado")){
                comuna.getView().getChildren().removeAll(avatarInf, infBorder);
                this.avatarInf = null;
                this.infBorder = null;
                this.avatarRec = createRec();
                comuna.getView().getChildren().add(avatarRec);
            }
        }
        if (pedestrian.getStatus().equals("susceptible")){
            if (_newstatus.equals("vacunado")){
                comuna.getView().getChildren().removeAll(avatarInf, infBorder);
                this.avatarInf = null;
                this.infBorder = null;
                this.avatarVac = createVac();
                comuna.getView().getChildren().add(avatarVac);
            }
        }

    }
}
