import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.ArrayList;

public class Simulator {
    /*
    Variables creadas en esta clase de acceso privado para la clase
     */
    public Timeline animation;
    private final Comuna comuna;
    private final double simulationSamplingTime;
    public double simulationTime;
    private final double delta_t;
    private final int cant_p;
    private final int cant_inf;
    private final double speed, deltaAngle, I_time, distance;
    public ArrayList<Pedestrian> Pedestrian_list = new ArrayList<>();
    public double rate;

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
     */
    public Simulator (Stage2 _stage2, Scene _mScene, double framePerSecond, double simulationTime2realTimeRate, Comuna comuna, double _delta_t, int _cant_p, int _cant_inf, double _speed, double _deltaAngle, double _I_time, double _distance){
        this.comuna = comuna;
        this.cant_p = _cant_p;
        this.cant_inf = _cant_inf;
        this.speed = _speed;
        this.deltaAngle = _deltaAngle;
        this.I_time = _I_time;
        this.distance = _distance;
        this.delta_t = _delta_t;
        this.rate = 1;
        double viewRefreshPeriod = 1 / framePerSecond;
        buildPedestrianArray();
        simulationSamplingTime = viewRefreshPeriod * simulationTime2realTimeRate;
        simulationTime = 0;

        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod * 1000), e->takeAction()));
        animation.setCycleCount(Timeline.INDEFINITE);
    }
    /**
     * @method takeAction : realiza el computo del tiempo y del siguiente estado
     * segun lo necesite, haciendo la llamada a actualizar el estado de una comuna.
     */
    private void takeAction() {
        double nextStop = simulationTime + simulationSamplingTime;
        for(; simulationTime < nextStop; simulationTime += delta_t) {
            comuna.computeNextState(delta_t, Pedestrian_list);
            comuna.updateState(Pedestrian_list);
            comuna.updateView(Pedestrian_list);
            comuna.detectInfected(Pedestrian_list, simulationTime, distance);
            comuna.checkIfRecovered(simulationTime, Pedestrian_list, I_time);
        }
    }
    /**
     * @method buildPedestrianArray : realiza la creacion del array de tipo pedestrian
     */
    public void buildPedestrianArray(){
        int j;
        for (j = 0; j < (cant_p - cant_inf); j++){
            Pedestrian person = new Pedestrian(this.comuna, speed, deltaAngle, "susceptible");
            this.Pedestrian_list.add(person);
        }
        for (int i = j; i < cant_p; i++){
            Pedestrian person = new Pedestrian(this.comuna, speed, deltaAngle, "infectado");
            this.Pedestrian_list.add(person);
        }
    }
    /**
     * @method start : realiza el llamado a animation.play()
     */
    public void start(SimulatorMenuBar _simMenu){
        rate = 1.0;
        detectAnimationRate();
        animation.play();
        _simMenu.hideSettings();
    }
    /**
     * @method detectAnimationRate : settea el rate de una animacion
     */
    public void detectAnimationRate(){
        animation.setRate(rate);
    }
    /**
     * @method stop : realiza el llamado a animation.stop()
     */
    public void stop(SimulatorMenuBar _simMenu){
        animation.stop();
        _simMenu.showSettings();
    }
}
