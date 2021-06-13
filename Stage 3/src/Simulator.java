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
    private final double p0, p1, p2;
    private final double maskFraction;
    private final int cant_p, cant_inf;
    private final double speed, deltaAngle, I_time, distance;
    public ArrayList<Pedestrian> Pedestrian_list = new ArrayList<>();
    public double rate;
    private final Graph graph;

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
     */
    public Simulator (Stage3 _stage3, Scene _mScene, double framePerSecond, double simulationTime2realTimeRate, Graph _graph, Comuna comuna, double _delta_t, int _cant_p, int _cant_inf, double _speed, double _deltaAngle, double _I_time, double _distance, double _maskFraction, double _p0, double _p1, double _p2){
        this.comuna = comuna;
        this.cant_p = _cant_p;
        this.cant_inf = _cant_inf;
        this.speed = _speed;
        this.deltaAngle = _deltaAngle;
        this.I_time = _I_time;
        this.distance = _distance;
        this.delta_t = _delta_t;
        this.rate = 1;
        this.p0 = _p0;
        this.p1 = _p1;
        this.p2 = _p2;
        this.maskFraction = _maskFraction;
        double viewRefreshPeriod = 1 / framePerSecond;
        buildPersonArray();
        simulationSamplingTime = viewRefreshPeriod * simulationTime2realTimeRate;
        simulationTime = 0;


        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod * 1000), e->takeAction()));
        animation.setCycleCount(Timeline.INDEFINITE);
        graph = _graph;
    }

    /**
     * @method takeAction : realiza el computo del tiempo y del siguiente estado
     * segun lo necesite, haciendo la llamada a actualizar el estado de una comuna.
     */
    private void takeAction() {
        double simtime = simulationTime;
        double nextStop = simulationTime + simulationSamplingTime;
        for(; simulationTime < nextStop; simulationTime += delta_t) {
            comuna.computeNextState(delta_t, Pedestrian_list);
            comuna.updateState(Pedestrian_list);
            comuna.updateView(Pedestrian_list);
            comuna.detectInfected(Pedestrian_list, simulationTime, distance, p0, p1, p2);
            comuna.checkIfRecovered(simulationTime, Pedestrian_list, I_time);
        }
        if (simtime != simulationTime){
            graph.updateChart((int)simulationTime, comuna.Susceptible_Q, comuna.Infected_Q, comuna.Recovered_Q);
        }
    }
    /**
     * @method buildPedestrianArray : realiza la creacion del array de tipo pedestrian
     */
    public void buildPersonArray(){
        int x, y, j, i;
        for (x = 0; x < Math.round((cant_p - cant_inf) * maskFraction); x++){
            Pedestrian person = new Pedestrian(this.comuna, speed, deltaAngle, "susceptible", true);
            this.Pedestrian_list.add(person);
        }
        for (y = x; y < Math.round(cant_p - cant_inf); y++){
            Pedestrian person = new Pedestrian(this.comuna, speed, deltaAngle, "susceptible", false);
            this.Pedestrian_list.add(person);
        }
        for (j = 0; j < Math.round((cant_inf) * maskFraction); j++){
            Pedestrian person = new Pedestrian(this.comuna, speed, deltaAngle, "infectado", true);
            this.Pedestrian_list.add(person);
        }
        for (i = j; i < cant_inf; i++){
            Pedestrian person = new Pedestrian(this.comuna, speed, deltaAngle, "infectado", false);
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
