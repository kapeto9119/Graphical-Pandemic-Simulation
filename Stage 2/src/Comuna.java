import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.media.AudioClip;
import java.util.ArrayList;

public class Comuna {
    /*
    Variables creadas en esta clase, privadas para hacer uso en esta clase.
     */
    private Rectangle2D territory;
    public ComunaView view;
    private double Infected_Q, Susceptible_Q, Recovered_Q;
    private static final AudioClip clip1 = new AudioClip("https://wavlist.com/wav/synthesizer02.wav");
    /*
    Se usa un constructor para inicializar territory con valores predefinidos,
    tambien se inicializa el atributo person a nul
    */
    public Comuna(){
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        Infected_Q = SimulatorConfig.I;
        Susceptible_Q = SimulatorConfig.N - SimulatorConfig.I;
        Recovered_Q = 0;
        territory = new Rectangle2D(0,0, width, length);
        view = new ComunaView(this);
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
    public void detectInfected(ArrayList<Pedestrian> _PedestrianList, double time, double distance){

        for (int i = 0; i < _PedestrianList.size(); i++){
            for (int j = i + 1; j < _PedestrianList.size(); j++){
                if (DistanceB2P(_PedestrianList.get(i), _PedestrianList.get(j)) < distance){
                    if (_PedestrianList.get(i).getStatus() == "susceptible" && _PedestrianList.get(j).getStatus() == "infectado"){
                        double probability = 0.7;
                        double r = Math.floor(Math.random() * 100) / 100;
                        if (r <= probability){
                            _PedestrianList.get(i).setStatus("infectado");
                            _PedestrianList.get(i).setInfectionTime(time);
                            clip1.setVolume(0.3);
                            clip1.play();
                            this.Infected_Q += 1;
                            this.Susceptible_Q -= 1;
                        }
                    }
                    if (_PedestrianList.get(i).getStatus() == "infectado" && _PedestrianList.get(j).getStatus() == "susceptible"){
                        double probability = 0.7;
                        double r = Math.floor(Math.random() * 100) / 100;
                        if (r <= probability){
                            _PedestrianList.get(j).setStatus("infectado");
                            _PedestrianList.get(j).setInfectionTime(time);
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
    /*
    metodo checkIfRecovered, segun un tiempo determinado, cambia el status de un individuo a recuperado
     * @double _t = tiempo en segundos
     * @ArrayList<Individuo> = arraylist de tipo individuo
     * @double _I_time = tiempo en que un individuo tarda en curarse
    */
    public void checkIfRecovered(double _t, ArrayList<Pedestrian> _PedestrianList, double _I_time){
//        for (Pedestrian pedestrian : _PedestrianList) {
        for(int i = 0; i < _PedestrianList.size(); i++){
            if (_PedestrianList.get(i).getStatus().equals("infectado")){
                if ((_t - _PedestrianList.get(i).getInfectionTime()) > _I_time){
                    _PedestrianList.get(i).setStatus("recuperado");
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
