package sample;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

public class Comuna {
    private final Pedestrian person;
    private final Rectangle2D territory;
    private final ComunaView view;

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
    public double getWidth() {
        return territory.getWidth();
    }
    public double getHeight() {
        return territory.getHeight();
    }
    public void computeNextState (double delta_t) {
        person.computeNextState(delta_t);
    }
    public void updateState () {
        person.updateState();
    }
    public void updateView(){
        view.update(person);
    }
    public Group getView() {
        return view;
    }
 }
