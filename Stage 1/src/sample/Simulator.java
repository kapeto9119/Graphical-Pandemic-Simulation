package sample;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {
    private Timeline animation;
    private Comuna comuna;
    private double simulationSamplingTime;
    private double simulationTime;  // it goes along with real time, faster or slower than real time
    private double delta_t;   // precision of discrete simulation time

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
     */
    public Simulator (double framePerSecond, double simulationTime2realTimeRate, Comuna comuna){
        this.comuna = comuna;
        double viewRefreshPeriod = 1 / framePerSecond;
        simulationSamplingTime = viewRefreshPeriod * simulationTime2realTimeRate;
        delta_t = SimulatorConfig.DELTA_T;
        simulationTime = 0;

        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod * 1000), e->takeAction()));
        animation.setCycleCount(Timeline.INDEFINITE);

    }
    private void takeAction() {
        double nextStop = simulationTime + simulationSamplingTime;
        for(; simulationTime < nextStop; simulationTime += delta_t) {
            comuna.computeNextState(delta_t);
            comuna.updateState();
            comuna.updateView();
        }
    }
    public void start(){
        animation.play();
    }

    public void stop(){
        animation.stop();
    }
}
