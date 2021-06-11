package sample;

public class Pedestrian {
    private double x, y, speed, angle, deltaAngle;
    private double x_tPlusDelta, y_tPlusDelta;
    private Comuna comuna;
    public PedestrianView pedestrianView;
    private String status;
    private double infected_time;

    public Pedestrian(Comuna comuna, double speed, double deltaAngle, String _status){
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle = deltaAngle;
        this.status = _status;
        x = Math.random() * comuna.getWidth();
        y = Math.random() * comuna.getHeight();
        angle = Math.random() * 2 * Math.PI;
        pedestrianView = new PedestrianView(comuna, this);
        infected_time = 0;
    }
    public void setInfectionTime(double _t){
        this.infected_time = _t;
    }
    public double getInfectionTime(){
        return this.infected_time;
    }
    public void setStatus(String newstatus) {
        pedestrianView.setForm(this, newstatus);
        this.status = newstatus;
    }
    public String getStatus(){
        return this.status;
    }
    public double getX(){
        return this.x;
    }
    public double getY() {
        return this.y;
    }
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
    public void updateState(){
        x = x_tPlusDelta;
        y = y_tPlusDelta;
    }
    public void updateView() {
        pedestrianView.update(this);
    }
}
