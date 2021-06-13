public class Pedestrian {
    /*
Variables creadas en esta clase, privadas.
*/
    private double x;
    private double y;
    private final double speed;
    private double angle;
    private final double deltaAngle;
    private double x_tPlusDelta, y_tPlusDelta;
    private final Comuna comuna;
    public PedestrianView pedestrianView;
    private String status;
    private double infected_time;
    private final boolean maskOn;
    /**
     * @method Pedestrian : contructor que realiza la asignacion de diferentes datos.
     * @param Comuna c : se requiere como argumento un objeto de tipo Comuna
     * @param double speed : speed con la que se mueve un individuo
     * @param double deltaAngle : angulo de movimiento
     * @param String _status : estado que muestra si esta infectado o no
     */
    public Pedestrian(Comuna comuna, double speed, double deltaAngle, String _status, boolean _maskOn){
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle = deltaAngle;
        this.status = _status;
        x = Math.random() * comuna.getWidth();
        y = Math.random() * comuna.getHeight();
        angle = Math.random() * 2 * Math.PI;
        maskOn = _maskOn;
        pedestrianView = new PedestrianView(comuna, this);
        infected_time = 0;
    }
    /*
     * checker para revisar si tiene o no mascarilla
     */
    public boolean checkMask(){
        return this.maskOn;
    }
    /*
     * @param double _t : recibe un double que hace referencia al tiempo en segundos
     */
    public void setInfectionTime(double _t){
        this.infected_time = _t;
    }
    /*
@return this.infected_time = retorna el tiempo de infeccion
*/
    public double getInfectionTime(){
        return this.infected_time;
    }
    /*
@param String setStatus = corresponde un setter de status para reemplazar el estado de un individuo
*/
    public void setStatus(String newstatus) {
        pedestrianView.setForm(this, newstatus);
        this.status = newstatus;
    }
    /*
 @return this.status =  retorna el estado de una persona
 */
    public String getStatus(){
        return this.status;
    }
    /*
getter de la posicion x
*/
    public double getX(){
        return this.x;
    }
    /*
getter de la posicion y
*/
    public double getY() {
        return this.y;
    }
    /*
 Metodo que computa la siguiente posicion del individuo.
 */
    public void computeNextState(double delta_t) {
        double r = Math.random();
        angle += deltaAngle * (1 - 2 * r);
        x_tPlusDelta = x + speed * Math.cos(angle) * delta_t;
        y_tPlusDelta = y + speed * Math.sin(angle) * delta_t;
        if (x_tPlusDelta < 0){   // rebound logic
            x_tPlusDelta =- x_tPlusDelta;
            angle = Math.PI-angle;
        }
        if (y_tPlusDelta < 0){
            y_tPlusDelta =- y_tPlusDelta;
            angle = 2 * Math.PI - angle;
        }
        if (x_tPlusDelta > comuna.getWidth()){
            x_tPlusDelta = 2 * comuna.getWidth() - x_tPlusDelta;
            angle = Math.PI - angle;
        }
        if (y_tPlusDelta > comuna.getHeight()){
            y_tPlusDelta = 2 * comuna.getHeight() - y_tPlusDelta;
            angle = 2 * Math.PI - angle;
        }
    }
    /*
Actualiza el estado, es decir, posicion de x e y basado en el calculo realizado en el metodo "computeNextState()".
*/
    public void updateState(){
        x = x_tPlusDelta;
        y = y_tPlusDelta;
    }
    /*
Actualiza el estado, es decir, posicion de x e y basado en el calculo realizado en el metodo "computeNextState()" para mostrarla por la interfaz
*/
    public void updateView() {
        pedestrianView.update(this);
    }
}
