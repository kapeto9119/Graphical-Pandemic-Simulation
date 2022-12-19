import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;

public class Comuna {
    /*
    Variables creadas en esta clase, privadas para hacer uso en esta clase.
    */
    private Pedestrian person;
    private Rectangle2D territory;
    public ComunaView view;
    public double Infected_Q, Susceptible_Q, Recovered_Q, Vaccinated_Q;
    private final AudioClip clip1;
    boolean mute;

    /*
    Se usa un constructor para inicializar territory con valores predefinidos,
    tambien se inicializa el atributo person a nul
    */
    public Comuna(String file, boolean _mute){
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        Infected_Q = SimulatorConfig.I;
        Susceptible_Q = SimulatorConfig.N - SimulatorConfig.I;
        Recovered_Q = 0;
        territory = new Rectangle2D(0,0, width, length);
        view = new ComunaView(this);
        clip1 = new AudioClip("file:" + file);
        mute = _mute;
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
    public void computeNextState (double delta_t, ArrayList<Pedestrian> _PedestrianList) {
        for (Pedestrian pedestrian : _PedestrianList) {
            pedestrian.computeNextState(delta_t);
        }
    }
    /*
    Método que actualiza la posicion de la persona dentro de la comuna,
    llama al método público "updateState()" de la clase Individuo.
     */
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
    /*
    metodo detectInfected que realiza un chequeo sobre individuos para infectar o generar
    un cambio de status segun lo necesite.
     * @ArrayList<Pedestrian> _PedestrianList = arraylist de tipo Pedestrian
     * @double time = tiempo en segundos
     * @double distance = distancia entre individuos
    */
    public void detectInfected(ArrayList<Pedestrian> _IndividuosList, double time, double distance, double _p0, double _p1, double _p2){
        for (int i = 0; i < _IndividuosList.size(); i++){
            for (int j = i + 1; j < _IndividuosList.size(); j++){
                if (DistanceB2P(_IndividuosList.get(i), _IndividuosList.get(j)) < distance){
                    if (_IndividuosList.get(i).getStatus() == "susceptible" && _IndividuosList.get(j).getStatus() == "infectado"){
                        if (_IndividuosList.get(i).checkMask() == true && _IndividuosList.get(j).checkMask() == true){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p2){
                                _IndividuosList.get(i).setStatus("infectado");
                                _IndividuosList.get(i).setInfectionTime(time);
                                if (!mute){
                                    clip1.setVolume(0.7);
                                    clip1.play();
                                }
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if ((_IndividuosList.get(i).checkMask() == true && _IndividuosList.get(j).checkMask() == false) || (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == true)){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p1){
                                _IndividuosList.get(i).setStatus("infectado");
                                _IndividuosList.get(i).setInfectionTime(time);
                                if (!mute){
                                    clip1.setVolume(0.7);
                                    clip1.play();
                                }
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == false){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p0){
                                _IndividuosList.get(i).setStatus("infectado");
                                _IndividuosList.get(i).setInfectionTime(time);
                                if (!mute){
                                    clip1.setVolume(0.7);
                                    clip1.play();
                                }
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
                                if (!mute){
                                    clip1.setVolume(0.7);
                                    clip1.play();
                                }
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if ((_IndividuosList.get(i).checkMask() == true && _IndividuosList.get(j).checkMask() == false) || (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == true)){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p1){
                                _IndividuosList.get(j).setStatus("infectado");
                                _IndividuosList.get(j).setInfectionTime(time);
                                if (!mute){
                                    clip1.setVolume(0.7);
                                    clip1.play();
                                }
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }
                        if (_IndividuosList.get(i).checkMask() == false && _IndividuosList.get(j).checkMask() == false){
                            if ((Math.floor(Math.random() * 100) / 100) <= _p0){
                                _IndividuosList.get(j).setStatus("infectado");
                                _IndividuosList.get(j).setInfectionTime(time);
                                if (!mute){
                                    clip1.setVolume(0.7);
                                    clip1.play();
                                }
                                this.Infected_Q += 1;
                                this.Susceptible_Q -= 1;
                            }
                        }

                    }

                }
            }
        }
    }
    /*
    metodo checkIfRecovered, segun un tiempo determinado, cambia el status de un individuo a recuperado
     * @double _t = tiempo en segundos
     * @ArrayList<Individuo> = arraylist de tipo individuo
     * @double _I_time = tiempo en que un individuo tarda en curarse
    */
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
    /*
    Método que actualiza la posicion de la persona dentro del view,
    llama al método view.update(person)
     */
    public void updateView (ArrayList<Pedestrian> _PedestrianList) {
        for (Pedestrian pedestrian : _PedestrianList) {
            view.update(pedestrian);
        }
    }
    /*
    Método que retorna un objeto de tipo Group
     */
    public Group getView() {
        return view;
    }
    /*
    Método que realiza el llamado a usar el metodo view.getChildren().clear()
     */
    public void refreshView(){
        view.getChildren().clear();
    }
 }
