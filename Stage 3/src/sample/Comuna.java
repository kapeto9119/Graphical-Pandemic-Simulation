package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;

public class Comuna {
    private Pedestrian person;
    private Rectangle2D territory;
    public ComunaView view;
    public double Infected_Q, Susceptible_Q, Recovered_Q;
    private static final AudioClip clip1 = new AudioClip("https://wavlist.com/wav/synthesizer02.wav");

    public Comuna(){
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        Infected_Q = SimulatorConfig.I;
        Susceptible_Q = SimulatorConfig.N - SimulatorConfig.I;
        Recovered_Q = 0;
        territory = new Rectangle2D(0,0, width, length);
        double speed = SimulatorConfig.SPEED;
        double deltaAngle = SimulatorConfig.DELTA_THETA;
        view = new ComunaView(this);
    }

    public double getWidth() {
        return territory.getWidth();
    }

    public double getHeight() {
        return territory.getHeight();
    }

    public void computeNextState (double delta_t, ArrayList<Pedestrian> _PedestrianList) {
        for (Pedestrian pedestrian : _PedestrianList) {
            pedestrian.computeNextState(delta_t);
        }
    }

    public void updateState(ArrayList<Pedestrian> _PedestrianList) {
        for (Pedestrian pedestrian : _PedestrianList) {
            pedestrian.updateState();
        }
    }

    public double DistanceB2P(Pedestrian _A, Pedestrian _B){ //Distance between 2 points
        double X = Math.pow((_A.getX() - _B.getX()), 2);
        double Y = Math.pow((_A.getY() - _B.getY()), 2);
        return Math.sqrt(X + Y);
    }

    public void detectInfected(ArrayList<Pedestrian> _IndividuosList, double time, double distance, double _p0, double _p1, double _p2){
        for (int i = 0; i < _IndividuosList.size(); i++){
            for (int j = i + 1; j < _IndividuosList.size(); j++){
                if (DistanceB2P(_IndividuosList.get(i), _IndividuosList.get(j)) < distance){
                    if (_IndividuosList.get(i).getStatus() == "susceptible" && _IndividuosList.get(j).getStatus() == "infectado"){
                        if (_IndividuosList.get(i).checkMask() == true && _IndividuosList.get(j).checkMask() == true){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p2){
                                _IndividuosList.get(i).setStatus("infectado");
                                _IndividuosList.get(i).setInfectionTime(time);
                                clip1.setVolume(0.3);
                                clip1.play();
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if ((_IndividuosList.get(i).checkMask() == true && _IndividuosList.get(j).checkMask() == false) || (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == true)){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p1){
                                _IndividuosList.get(i).setStatus("infectado");
                                _IndividuosList.get(i).setInfectionTime(time);
                                clip1.setVolume(0.3);
                                clip1.play();
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == false){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p0){
                                _IndividuosList.get(i).setStatus("infectado");
                                _IndividuosList.get(i).setInfectionTime(time);
                                clip1.setVolume(0.3);
                                clip1.play();
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                    }
                    if (_IndividuosList.get(i).getStatus() == "infectado" && _IndividuosList.get(j).getStatus() == "susceptible"){
                        if (_IndividuosList.get(i).checkMask() == true && _IndividuosList.get(j).checkMask() == true){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p2){
                                _IndividuosList.get(j).setStatus("infectado");
                                _IndividuosList.get(j).setInfectionTime(time);
                                clip1.setVolume(0.3);
                                clip1.play();
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if ((_IndividuosList.get(i).checkMask() == true && _IndividuosList.get(j).checkMask() == false) || (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == true)){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p1){
                                _IndividuosList.get(j).setStatus("infectado");
                                _IndividuosList.get(j).setInfectionTime(time);
                                clip1.setVolume(0.3);
                                clip1.play();
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == false){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p0){
                                _IndividuosList.get(j).setStatus("infectado");
                                _IndividuosList.get(j).setInfectionTime(time);
                                clip1.setVolume(0.3);
                                clip1.play();
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }

                    }

                }
            }
        }
    }

    public void checkIfRecovered(double _t, ArrayList<Pedestrian> _PedestrianList, double _I_time){
        for (Pedestrian pedestrian : _PedestrianList) {
            if (pedestrian.getStatus().equals("infectado")) {
                if ((_t - pedestrian.getInfectionTime()) > _I_time) {
                    pedestrian.setStatus("recuperado");
                    this.Infected_Q -= 1;
                    this.Recovered_Q += 1;

                }
            }
        }
    }

    public void updateView (ArrayList<Pedestrian> _PedestrianList) {
        for (Pedestrian pedestrian : _PedestrianList) {
            view.update(pedestrian);
        }
    }

    public Pedestrian getPedestrian() {
        return person;
    }

    public Group getView() {
        return view;
    }

    public void refreshView(){
        view.getChildren().clear();
    }
 }
