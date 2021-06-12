package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.ArrayList;

public class Simulator {
    public Timeline animation;
    private Comuna comuna;
    private double simulationSamplingTime;
    public double simulationTime;
    private double delta_t;
    private double p0, p1, p2;
    private double maskFraction;
    private int cant_p, cant_inf;
    private double speed, deltaAngle, I_time, distance;
    public ArrayList<Pedestrian> Pedestrian_list = new ArrayList<>();
    private Scene mScene;
    private Stage4 _stage4;
    public double rate;
    private Graph graph;
    private boolean initV;
    private boolean detVac;
    public VaccinationCentre vacss;

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
     */
    public Simulator (Stage4 _stage4, Scene _mScene, double framePerSecond, double simulationTime2realTimeRate, Graph _graph, Comuna comuna, double _delta_t, int _cant_p, int _cant_inf, double _speed, double _deltaAngle, double _I_time, double _distance, double _maskFraction, double _p0, double _p1, double _p2){
        this.mScene = _mScene;
        this._stage4 = _stage4;
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
        this.vacss = new VaccinationCentre(comuna);
        this.maskFraction = _maskFraction;
        double viewRefreshPeriod = 1 / framePerSecond;
        buildPersonArray();
        simulationSamplingTime = viewRefreshPeriod * simulationTime2realTimeRate;
        simulationTime = 0;
        initV = true;
        detVac = false;

        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod * 1000), e->takeAction()));
        animation.setCycleCount(Timeline.INDEFINITE);
        graph = _graph;
        System.out.println("SIMULATOR VACTIME: " + SimulatorConfig.VAC_TIME);
    }

    private void takeAction() {
        double simtime = simulationTime;
        double nextStop = simulationTime + simulationSamplingTime;
        for(; simulationTime < nextStop; simulationTime += delta_t) {
            comuna.computeNextState(delta_t, Pedestrian_list);
            comuna.updateState(Pedestrian_list);
            comuna.updateView(Pedestrian_list);
            comuna.detectInfected(Pedestrian_list, simulationTime, distance, p0, p1, p2);
            comuna.checkIfRecovered(simulationTime, Pedestrian_list, I_time);
            if (detVac){
                vacss.detectVaccineCentre(Pedestrian_list);
            }
        }
        System.out.println(simulationTime);
        if (Math.round(simulationTime) == SimulatorConfig.VAC_TIME){
            if (initV){
                System.out.println("initV: " + SimulatorConfig.VAC_TIME + ", simtime: " + simulationTime);
                detVac = true;
                vacss.initVac();
                initV = false;
            }
        }
        if (simtime != simulationTime){
            graph.updateChart((int)simulationTime, comuna.Susceptible_Q, comuna.Infected_Q, comuna.Recovered_Q, comuna.Vaccinated_Q);
        }
    }

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

    public void start(SimulatorMenuBar _simMenu){
        rate = 1.0;
        detectAnimationRate();
        animation.play();
        _simMenu.hideSettings();
    }

    public void detectAnimationRate(){
        animation.setRate(rate);
    }

    public void stop(SimulatorMenuBar _simMenu){
        animation.stop();
        _simMenu.showSettings();
    }
}
