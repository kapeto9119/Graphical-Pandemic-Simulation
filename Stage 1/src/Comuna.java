import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

public class Comuna {
    /*
    Variables creadas en esta clase, privadas de tipo Pedestrian, Rectangle y ComunaView
    */
    private final Pedestrian person;
    private final Rectangle2D territory;
    private final ComunaView view;
    /*
    Se usa un constructor para inicializar territory con valores predefinidos,
    tambien se inicializa el atributo person a nul
    */
    public Comuna(){
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        System.out.println(width + ", " + length);
        territory = new Rectangle2D(0,0, width, length);
        double speed = SimulatorConfig.SPEED;
        double deltaAngle = SimulatorConfig.DELTA_THETA;
        view = new ComunaView(this);
        person = new Pedestrian(this, speed, deltaAngle);
    }
    /*
    Se crea el metodo getWidth para obtener el ancho de la comuna.
    */
    public double getWidth() {
        return territory.getWidth();
    }
    /*
    Se crea el metodo getHeight para obtener el alto de la comuna.
     */
    public double getHeight() {
        return territory.getHeight();
    }
    /*
    Método que computa el siguiente estado de la persona dentro de la comuna (atributo interno),
    lo hace llamando a un metodo público de clase Individuo.
    */
    public void computeNextState (double delta_t) {
        person.computeNextState(delta_t);
    }

    /*
    Método que actualiza la posicion de la persona dentro de la comuna,
    llama al método público "updateState()" de la clase Individuo.
     */
    public void updateState () {
        person.updateState();
    }

    /*
    Método que actualiza la posicion de la persona dentro del view,
    llama al método view.update(person)
     */
    public void updateView(){
        view.update(person);
    }

   /*
    Método que retorna un objeto de tipo Group
     */
    public Group getView() {
        return view;
    }
 }
