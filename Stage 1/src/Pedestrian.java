public class Pedestrian {

    /*
    Variables creadas en esta clase, privadas.
    */
    private double x, y, speed, angle, deltaAngle;
    private double x_tPlusDelta, y_tPlusDelta;
    private final Comuna comuna;
    private final PedestrianView pedestrianView;

    /**
     * @method Pedestrian : contructor que realiza la asignacion de diferentes datos.
     * @param Comuna c : se requiere como argumento un objeto de tipo Comuna
     * @param double speed : speed con la que se mueve un individuo
     * @param double deltaAngle : angulo de movimiento
     */
    public Pedestrian(Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle=deltaAngle;
        x = Math.random() * comuna.getWidth();
        y = Math.random() * comuna.getHeight();
        angle = Math.random() * 2 * Math.PI;
        pedestrianView = new PedestrianView(comuna, this);
    }

    /*
    getter de la posicion x
    */
    public double getX(){
        return x;
    }

    /*
    getter de la posicion y
    */
    public double getY() {
        return y;
    }

    /*
    Metodo que computa la siguiente posicion del individuo.
    */
    public void computeNextState(double delta_t) {
        double r = Math.random();
        angle += deltaAngle * (1 - 2 * r);
        /*
        x_tPlusDelta y y_tPlusDelta seria el resultado de evaluar la posicion inicial
        x, y de mi individuo y que este se desplace a un nuevo lugar dentro de la
        comuna, donde este nuevo lugar tiene que ser acotado ahora
        */
        x_tPlusDelta = x + speed * Math.cos(angle) * delta_t;
        y_tPlusDelta = y + speed * Math.sin(angle) * delta_t;
        /*
        Este condicional cumple la funcion de que, tocado un valor igual al
        maximo o minimo asignado para el eje x o el eje y
        entonces debe rebotar para que se quede
        dentro de la comuna.
        */
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
    Actualiza el estado, para mostrarlo
    */
    public void updateView() {
        pedestrianView.update(this);
    }
}
