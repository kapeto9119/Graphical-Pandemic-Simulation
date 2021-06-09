package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public class Simulator {
    private Timeline animation;
    private Comuna comuna;
    private double simulationSamplingTime;
    private double simulationTime;  // it goes along with real time, faster or slower than real time
    private double delta_t;   // precision of discrete simulation time
    private int cant_p, cant_inf;
    private double speed, deltaAngle, I_time, distance;
    private boolean isPlaying;
    public ArrayList<Pedestrian> Pedestrian_list = new ArrayList<>();

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
     */
    public Simulator (double framePerSecond, double simulationTime2realTimeRate, Comuna comuna, int _cant_p, int _cant_inf, double _speed, double _deltaAngle, double _I_time, double _distance){
        this.comuna = comuna;
        this.cant_p = _cant_p;
        this.cant_inf = _cant_inf;
        this.speed = _speed;
        this.deltaAngle = _deltaAngle;
        this.I_time = _I_time;
        this.distance = _distance;
        this.isPlaying = false;
        double viewRefreshPeriod = 1 / framePerSecond; // in [ms] real time used to display
        // a new view on application
        simulationSamplingTime = viewRefreshPeriod * simulationTime2realTimeRate;
        delta_t = SimulatorConfig.DELTA_T;
        simulationTime = 0;

        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod * 1000), e->takeAction()));
        animation.setCycleCount(Timeline.INDEFINITE);

    }

    public boolean isPlaying(){
        return isPlaying;
    }

    private void takeAction() {
        double nextStop = simulationTime + simulationSamplingTime;
        for(; simulationTime < nextStop; simulationTime += delta_t) {
            comuna.computeNextState(delta_t, Pedestrian_list); // compute its next state based on current global state
            comuna.updateState(Pedestrian_list);             // update its state
            comuna.updateView(Pedestrian_list);
            comuna.detectInfected(Pedestrian_list, simulationTime, distance);
            comuna.checkIfRecovered(simulationTime, Pedestrian_list, I_time);
        }
        //???
    }

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

    public void start(SimulatorMenuBar _simMenu){
        animation.play();
        _simMenu.hideSettings();
        isPlaying = true;

        //comuna.getView().setOnKeyPressed( e->keyHandle(e));
    }
    private void keyHandle (KeyEvent e) {
	/// ?????
    }
    public void stop(SimulatorMenuBar _simMenu){
        animation.stop();
        _simMenu.showSettings();
        isPlaying = false;
    }
    public void speedup(){
       //????
    }
    public void slowdown(){
       // ???
    }
}
