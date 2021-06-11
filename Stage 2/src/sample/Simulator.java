package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public class Simulator {
    public Timeline animation;
    private Comuna comuna;
    private double simulationSamplingTime;
    public double simulationTime;
    private double delta_t;
    private int cant_p, cant_inf;
    private double speed, deltaAngle, I_time, distance;
    public ArrayList<Pedestrian> Pedestrian_list = new ArrayList<>();
    private Scene mScene;
    private Stage2 _stage2;
    public double rate;

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
     */
    public Simulator (Stage2 _stage2, Scene _mScene, double framePerSecond, double simulationTime2realTimeRate, Comuna comuna, double _delta_t, int _cant_p, int _cant_inf, double _speed, double _deltaAngle, double _I_time, double _distance){
        this.mScene = _mScene;
        this._stage2 = _stage2;
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

    private void takeAction() {
        double nextStop = simulationTime + simulationSamplingTime;
        for(; simulationTime < nextStop; simulationTime += delta_t) {
            comuna.computeNextState(delta_t, Pedestrian_list);
            comuna.updateState(Pedestrian_list);
            comuna.updateView(Pedestrian_list);
            comuna.detectInfected(Pedestrian_list, simulationTime, distance);
            comuna.checkIfRecovered(simulationTime, Pedestrian_list, I_time);
        }
        System.out.println(simulationTime);
        //detectArrow(mScene);
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
        rate = 1.0;
        detectAnimationRate();
        animation.play();
        _simMenu.hideSettings();
    }

    public void detectAnimationRate(){
        System.out.println(rate);
        animation.setRate(rate);
    }

    public void stop(SimulatorMenuBar _simMenu){
        animation.stop();
        _simMenu.showSettings();
    }

//    public void speedup(){
//        System.out.println("SpeedUp");
//        animation.setRate(2.0);
//    }
//
//    public void slowdown(){
//        System.out.println("SlowDown");
//        animation.setRate(0.5);
//    }
}
