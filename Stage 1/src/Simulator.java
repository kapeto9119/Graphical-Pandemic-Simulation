import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {
    /*
    Variables creadas en esta clase de acceso privado para la clase
     */
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
    /**
     * @method takeAction : realiza el computo del tiempo y del siguiente estado
     * segun lo necesite, haciendo la llamada a actualizar el estado de una comuna.
     */
    private void takeAction() {
        double nextStop = simulationTime + simulationSamplingTime;
        for(; simulationTime < nextStop; simulationTime += delta_t) {
            comuna.computeNextState(delta_t);
            comuna.updateState();
            comuna.updateView();
        }
    }

    /**
     * @method start : realiza el llamado a animation.play()
     */
    public void start(){
        animation.play();
    }

    /**
     * @method stop : realiza el llamado a animation.stop()
     */
    public void stop(){
        animation.stop();
    }
}
